package com.example.printemps.loan.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.RenewLoanRequest;
import com.example.printemps.loan.application.usecases.RenewLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RenewLoanHandler implements RenewLoan {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final PenaltyRepository penaltyRepository;

    public RenewLoanHandler(
            LoanRepository loanRepository,
            UserRepository userRepository,
            PolicyRepository policyRepository, PenaltyRepository penaltyRepository
    ) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.penaltyRepository = penaltyRepository;
    }

    @Override
    @Transactional
    public Loan execute(RenewLoanRequest request) {
        Loan loan = loanRepository.findById(new LoanId(request.loanId()))
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + loan.getUserId()));

        Policy policy = policyRepository.findById(user.getCategory())
                .orElseThrow(() -> new RuntimeException("Policy not found for category: " + user.getCategory()));

        if (loan.getRenewCount() >= policy.getMaxRenewals()) {
            throw new IllegalStateException("Renewal limit of " + policy.getMaxRenewals() + " reached");
        }

        // TODO: vérifier qu'il n'existe pas de réservation en attente (à implémenter avec le module Hold)
        // TODO: vérifier qu'il n'y a pas de pénalité bloquante (à implémenter avec le module Penalty)
        if (!penaltyRepository.findActiveByUserId(loan.getUserId()).isEmpty()) {
            throw new IllegalStateException("Renewal not allowed: user has active penalties");
        }

        loan.renew(policy.getLoanDurationDays());

        return loanRepository.save(loan);
    }
}
