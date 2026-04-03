package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.CheckoutLoanRequest;
import com.example.printemps.loan.application.usecases.CheckoutLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.shared.DomainIdGenerator;
import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CheckoutLoanHandler implements CheckoutLoan {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final DomainIdGenerator idGenerator;

    public CheckoutLoanHandler(
            LoanRepository loanRepository,
            CopyRepository copyRepository,
            UserRepository userRepository,
            PolicyRepository policyRepository,
            DomainIdGenerator idGenerator
    ) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public Loan execute(CheckoutLoanRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.userId()));

        Policy policy = policyRepository.findById(user.getCategory())
                .orElseThrow(() -> new RuntimeException("Policy not found for category: " + user.getCategory()));

        Copy copy = copyRepository.findById(new CopyId(request.copyId()))
                .orElseThrow(() -> new RuntimeException("Copy not found: " + request.copyId()));

        if (copy.getStatus() != CopyStatus.AVAILABLE) {
            throw new IllegalStateException("Copy is not available: " + request.copyId());
        }

        long activeLoans = loanRepository.findActiveByUserId(request.userId()).size();
        if (activeLoans >= policy.getMaxLoans()) {
            throw new IllegalStateException("User has reached their loan quota of " + policy.getMaxLoans());
        }

        Loan loan = Loan.create(
                new LoanId(idGenerator.generate()),
                request.copyId(),
                request.userId(),
                policy.getLoanDurationDays()
        );
        Loan saved = loanRepository.save(loan);

        copy.updateStatus(CopyStatus.ON_LOAN);
        copyRepository.save(copy);

        return saved;
    }
}