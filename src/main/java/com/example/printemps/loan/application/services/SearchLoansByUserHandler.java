package com.example.printemps.loan.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.usecases.SearchLoansByUser;
import com.example.printemps.loan.domain.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchLoansByUserHandler implements SearchLoansByUser {

    private final LoanRepository loanRepository;

    public SearchLoansByUserHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Loan> execute(String userId) {
        return loanRepository.findByUserId(userId);
    }
}
