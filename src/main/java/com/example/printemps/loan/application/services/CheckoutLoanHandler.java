package com.example.printemps.loan.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.CheckoutLoanRequest;
import com.example.printemps.loan.application.usecases.CheckoutLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.shared.DomainIdGenerator;
import org.springframework.stereotype.Service;

@Service
public class CheckoutLoanHandler implements CheckoutLoan {

    private final LoanRepository loanRepository;
    private final DomainIdGenerator idGenerator;

    public CheckoutLoanHandler(LoanRepository loanRepository, DomainIdGenerator idGenerator) {
        this.loanRepository = loanRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Loan execute(CheckoutLoanRequest request) {
        Loan loan = Loan.create(
                new LoanId(idGenerator.generate()),
                request.copyId(),
                request.userId(),
                21
        );

        return loanRepository.save(loan);
    }
}