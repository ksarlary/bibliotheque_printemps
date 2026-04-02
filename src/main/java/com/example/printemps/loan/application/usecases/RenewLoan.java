package com.example.printemps.loan.application.usecases;

import com.example.printemps.loan.application.models.RenewLoanRequest;
import com.example.printemps.loan.domain.Loan;

public interface RenewLoan {
    Loan execute(RenewLoanRequest request);
}
