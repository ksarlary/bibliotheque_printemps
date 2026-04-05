package com.example.printemps.loan.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.usecases.SearchLoanById;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SearchLoanByIdHandler implements SearchLoanById {

    private final LoanRepository loanRepository;

    public SearchLoanByIdHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan execute(LoanId loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }
}