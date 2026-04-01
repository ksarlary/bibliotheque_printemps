package com.example.printemps.loan.application.usecases;

import com.example.printemps.loan.application.models.CheckoutLoanRequest;
import com.example.printemps.loan.domain.Loan;

public interface CheckoutLoan {
    Loan execute(CheckoutLoanRequest request);
}