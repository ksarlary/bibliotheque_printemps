package com.example.printemps.loan.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.RenewLoanRequest;
import com.example.printemps.loan.application.usecases.RenewLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import org.springframework.stereotype.Service;

@Service
public class RenewLoanHandler implements RenewLoan {

    private final LoanRepository loanRepository;

    public RenewLoanHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan execute(RenewLoanRequest request) {
        Loan loan = loanRepository.findById(new LoanId(request.loanId()))
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.renew(7);

        return loanRepository.save(loan);
    }
}
