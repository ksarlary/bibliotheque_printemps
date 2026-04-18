package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.DeclareDamagedRequest;
import com.example.printemps.loan.application.usecases.DeclareDamagedLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.penalties.domain.PenaltyType;
import com.example.printemps.shared.DomainIdGenerator;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class DeclareDamagedLoanHandler implements DeclareDamagedLoan {

    private static final Logger log = LoggerFactory.getLogger(DeclareDamagedLoanHandler.class);

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final PenaltyRepository penaltyRepository;
    private final DomainIdGenerator idGenerator;
    private final UserRepository userRepository;

    public DeclareDamagedLoanHandler(
            LoanRepository loanRepository,
            CopyRepository copyRepository,
            PenaltyRepository penaltyRepository,
            DomainIdGenerator idGenerator,
            UserRepository userRepository
    ) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.penaltyRepository = penaltyRepository;
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Loan execute(String loanId, DeclareDamagedRequest request) {
        Loan loan = loanRepository.findById(new LoanId(loanId))
                .orElseThrow(() -> new NoSuchElementException("Loan not found: " + loanId));

        Copy copy = copyRepository.findById(new CopyId(loan.getCopyId()))
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + loan.getCopyId()));

        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + loan.getUserId()));


        // L'exemplaire revient physiquement mais est endommagé : l'emprunt se termine
        loan.markAsDamagedReturn(LocalDateTime.now());
        loanRepository.save(loan);

        // La copie va en réparation, pas disponible pour la file d'attente
        copy.updateStatus(CopyStatus.DAMAGED);
        copyRepository.save(copy);

        Penalty penalty = Penalty.create(
                new PenaltyId(idGenerator.generate()),
                loan.getUserId(),
                PenaltyType.DAMAGED_COPY,
                request.repairAmount(),
                "Copy declared damaged for loan " + loanId
        );
        penaltyRepository.save(penalty);
        userRepository.save(user);

        log.info("[AUDIT] DECLARE_DAMAGED loanId={} copyId={} userId={} repairAmount={}",
                loanId, loan.getCopyId(), loan.getUserId(), request.repairAmount());

        return loan;
    }
}
