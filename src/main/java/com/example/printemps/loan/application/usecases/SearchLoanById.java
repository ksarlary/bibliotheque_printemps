package com.example.printemps.loan.application.usecases;

import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;

public interface SearchLoanById {
    Loan execute(LoanId loanId);
}