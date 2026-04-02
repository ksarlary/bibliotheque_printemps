package com.example.printemps.loan.application.usecases;

import com.example.printemps.loan.application.models.ReturnLoanRequest;
import com.example.printemps.loan.domain.Loan;

public interface ReturnLoan {
    Loan execute(ReturnLoanRequest request);
}