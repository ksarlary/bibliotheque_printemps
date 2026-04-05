package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.ReturnLoanRequest;
import com.example.printemps.loan.application.usecases.ReturnLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.util.NoSuchElementException;

@Service
public class ReturnLoanHandler implements ReturnLoan {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final HoldRepository holdRepository;

    public ReturnLoanHandler(LoanRepository loanRepository, CopyRepository copyRepository, HoldRepository holdRepository) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.holdRepository = holdRepository;
    }

    @Override
    @Transactional
    public Loan execute(ReturnLoanRequest request) {
        Loan loan = loanRepository.findById(new LoanId(request.loanId()))
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));

        Copy copy = copyRepository.findById(new CopyId(loan.getCopyId()))
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + loan.getCopyId()));

        loan.markAsReturned();
        Loan saved = loanRepository.save(loan);

        Optional<Hold> nextHold = holdRepository.findFirstByWorkIdAndStatusInOrderByQueuePositionAsc(
                copy.getWork().getId().value(),
                List.of(HoldStatus.REQUESTED)
        );

        if (nextHold.isPresent()) {
            Hold hold = nextHold.get();
            hold.markReadyForPickup(LocalDateTime.now().plusDays(3));
            holdRepository.save(hold);

            copy.updateStatus(CopyStatus.RESERVED);
        } else {
            copy.updateStatus(CopyStatus.AVAILABLE);
        }

        copy.updateStatus(CopyStatus.AVAILABLE);
        copyRepository.save(copy);

        return saved;
    }
}