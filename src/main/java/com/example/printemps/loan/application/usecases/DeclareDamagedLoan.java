package com.example.printemps.loan.application.usecases;

import com.example.printemps.loan.application.models.DeclareDamagedRequest;
import com.example.printemps.loan.domain.Loan;

public interface DeclareDamagedLoan {
    Loan execute(String loanId, DeclareDamagedRequest request);
}
