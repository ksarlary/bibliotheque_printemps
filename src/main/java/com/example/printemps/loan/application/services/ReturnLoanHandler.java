package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.ReturnLoanRequest;
import com.example.printemps.loan.application.usecases.ReturnLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.penalties.domain.PenaltyType;
import com.example.printemps.shared.DomainIdGenerator;
import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.Status;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.util.NoSuchElementException;

@Service
public class ReturnLoanHandler implements ReturnLoan {

    private static final Logger log = LoggerFactory.getLogger(ReturnLoanHandler.class);

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final HoldRepository holdRepository;
    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final DomainIdGenerator idGenerator;

    public ReturnLoanHandler(LoanRepository loanRepository, CopyRepository copyRepository, HoldRepository holdRepository, PenaltyRepository penaltyRepository, UserRepository userRepository, PolicyRepository policyRepository, DomainIdGenerator idGenerator) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.holdRepository = holdRepository;
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public Loan execute(ReturnLoanRequest request) {
        Loan loan = loanRepository.findById(new LoanId(request.loanId()))
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));

        Copy copy = copyRepository.findById(new CopyId(loan.getCopyId()))
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + loan.getCopyId()));

        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + loan.getUserId()));

        Policy policy = policyRepository.findById(user.getCategory())
                .orElseThrow(() -> new NoSuchElementException("Policy not found for category: " + user.getCategory()));

        LocalDateTime returnedAt = LocalDateTime.now();
        long lateDays = loan.lateDays(returnedAt);

        loan.markAsReturned(returnedAt);
        Loan saved = loanRepository.save(loan);

        if (lateDays > 0) {
            BigDecimal amount = switch (policy.getLateFeeMode()) {
                case FLAT -> policy.getLateFeeAmount();
                case PER_DAY -> policy.getLateFeeAmount().multiply(BigDecimal.valueOf(lateDays));
            };

            Penalty penalty = Penalty.create(
                    new PenaltyId(idGenerator.generate()),
                    user.getSsoId(),
                    PenaltyType.LATE_RETURN,
                    amount,
                    "Late return for loan " + loan.getId().value() + " (" + lateDays + " days late)"
            );

            penaltyRepository.save(penalty);

            log.info("[AUDIT] RETURN_LATE loanId={} copyId={} userId={} lateDays={} penaltyAmount={}",
                    loan.getId().value(), loan.getCopyId(), loan.getUserId(), lateDays, amount);

            if (lateDays > policy.getBlockAfterDaysLate()) {
                user.updateStatus(Status.BLOCKED);
                userRepository.save(user);
                log.info("[AUDIT] USER_BLOCKED userId={} reason=LATE_RETURN loanId={}",
                        loan.getUserId(), loan.getId().value());
            }
        } else {
            log.info("[AUDIT] RETURN loanId={} copyId={} userId={}",
                    loan.getId().value(), loan.getCopyId(), loan.getUserId());
        }

        // Priorité 1 : hold sur cet exemplaire précis
        Optional<Hold> nextHold = holdRepository.findFirstByCopyIdAndStatusInOrderByQueuePositionAsc(
                copy.getId().value(),
                List.of(HoldStatus.REQUESTED)
        );

        // Priorité 2 : file d'attente générale sur l'oeuvre (FIFO)
        if (nextHold.isEmpty()) {
            nextHold = holdRepository.findFirstByWorkIdAndStatusInOrderByQueuePositionAsc(
                    copy.getWork().getId().value(),
                    List.of(HoldStatus.REQUESTED)
            );
        }

        if (nextHold.isPresent()) {
            Hold hold = nextHold.get();
            hold.markReadyForPickup(LocalDateTime.now().plusDays(3));
            holdRepository.save(hold);

            copy.updateStatus(CopyStatus.RESERVED);
        } else {
            copy.updateStatus(CopyStatus.AVAILABLE);
        }

        copyRepository.save(copy);

        return saved;
    }
}