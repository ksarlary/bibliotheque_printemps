package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.RenewLoanRequest;
import com.example.printemps.loan.application.usecases.RenewLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.domain.LoanStatus;
import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.shared.error.BusinessException;
import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.Status;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import java.util.NoSuchElementException;

@Service
public class RenewLoanHandler implements RenewLoan {

    private static final Logger log = LoggerFactory.getLogger(RenewLoanHandler.class);

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final PenaltyRepository penaltyRepository;
    private final HoldRepository holdRepository;
    private final CopyRepository copyRepository;

    public RenewLoanHandler(
            LoanRepository loanRepository,
            UserRepository userRepository,
            PolicyRepository policyRepository,
            PenaltyRepository penaltyRepository,
            HoldRepository holdRepository,
            CopyRepository copyRepository
    ) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.penaltyRepository = penaltyRepository;
        this.holdRepository = holdRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    @Transactional
    public Loan execute(RenewLoanRequest request) {
        Loan loan = loanRepository.findById(new LoanId(request.loanId()))
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));

        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + loan.getUserId()));



        Policy policy = policyRepository.findById(user.getCategory())
                .orElseThrow(() -> new NoSuchElementException("Policy not found for category: " + user.getCategory()));

        Copy copy = copyRepository.findById(new CopyId(loan.getCopyId()))
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + loan.getCopyId()));

        LocalDateTime now = LocalDateTime.now();

        if (loan.getStatus() == LoanStatus.OVERDUE || loan.isOverdue(now)) {
            loan.markAsOverdue(now);
            loanRepository.save(loan);
            throw new BusinessException("Renewal not allowed: loan is overdue");
        }

        if (loan.getRenewCount() >= policy.getMaxRenewals()) {
            throw new BusinessException("Renewal limit of " + policy.getMaxRenewals() + " reached");
        }

        // TODO: vérifier qu'il n'existe pas de réservation en attente (à implémenter avec le module Hold)
        boolean hasWaitingHold = holdRepository.existsByWorkIdAndStatusIn(
                copy.getWork().getId().value(),
                List.of(HoldStatus.REQUESTED, HoldStatus.READY_FOR_PICKUP)
        );

        if (hasWaitingHold) {
            throw new BusinessException("Loan cannot be renewed because a hold is waiting for this work");
        }

        // TODO: vérifier qu'il n'y a pas de pénalité bloquante (à implémenter avec le module Penalty)
        if (!penaltyRepository.findActiveByUserId(loan.getUserId()).isEmpty()) {
            throw new BusinessException("Renewal not allowed: user has active penalties");
        }


        if (user.getStatus() == Status.BLOCKED || user.getStatus() == Status.SUSPENDED) {
            throw new BusinessException("Renewal not allowed: user account is " + user.getStatus());
        }

        loan.renew(policy.getLoanDurationDays());
        Loan saved = loanRepository.save(loan);

        log.info("[AUDIT] RENEW loanId={} copyId={} userId={} newDueAt={} renewCount={}",
                saved.getId().value(), saved.getCopyId(), saved.getUserId(), saved.getDueAt(), saved.getRenewCount());

        return saved;
    }
}
