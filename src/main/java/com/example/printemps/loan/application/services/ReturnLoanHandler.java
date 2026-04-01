package com.example.printemps.loan.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.ReturnLoanRequest;
import com.example.printemps.loan.application.usecases.ReturnLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import org.springframework.stereotype.Service;

@Service
public class ReturnLoanHandler implements ReturnLoan {

    private final LoanRepository loanRepository;

    public ReturnLoanHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan execute(ReturnLoanRequest request) {
        Loan loan = loanRepository.findById(new LoanId(request.loanId()))
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.markAsReturned();

        return loanRepository.save(loan);
    }
}