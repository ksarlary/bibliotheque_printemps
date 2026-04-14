package com.example.printemps.loan.application.usecases;

import com.example.printemps.loan.application.models.DeclareLostRequest;
import com.example.printemps.loan.domain.Loan;

public interface DeclareLostLoan {
    Loan execute(String loanId, DeclareLostRequest request);
}
