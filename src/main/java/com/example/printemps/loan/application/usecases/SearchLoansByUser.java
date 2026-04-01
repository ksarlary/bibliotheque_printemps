package com.example.printemps.loan.application.usecases;

import com.example.printemps.loan.domain.Loan;

import java.util.List;

public interface SearchLoansByUser {
    List<Loan> execute(String userId);
}