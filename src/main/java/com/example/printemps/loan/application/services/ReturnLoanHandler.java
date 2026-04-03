package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.ReturnLoanRequest;
import com.example.printemps.loan.application.usecases.ReturnLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReturnLoanHandler implements ReturnLoan {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;

    public ReturnLoanHandler(LoanRepository loanRepository, CopyRepository copyRepository) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    @Transactional
    public Loan execute(ReturnLoanRequest request) {
        Loan loan = loanRepository.findById(new LoanId(request.loanId()))
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.markAsReturned();
        Loan saved = loanRepository.save(loan);

        Copy copy = copyRepository.findById(new CopyId(loan.getCopyId()))
                .orElseThrow(() -> new RuntimeException("Copy not found: " + loan.getCopyId()));
        copy.updateStatus(CopyStatus.AVAILABLE);
        copyRepository.save(copy);

        return saved;
    }
}