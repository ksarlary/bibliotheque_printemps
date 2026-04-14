package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.DeclareDamagedRequest;
import com.example.printemps.loan.application.usecases.DeclareDamagedLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.penalties.domain.PenaltyType;
import com.example.printemps.shared.DomainIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class DeclareDamagedLoanHandler implements DeclareDamagedLoan {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final PenaltyRepository penaltyRepository;
    private final DomainIdGenerator idGenerator;

    public DeclareDamagedLoanHandler(
            LoanRepository loanRepository,
            CopyRepository copyRepository,
            PenaltyRepository penaltyRepository,
            DomainIdGenerator idGenerator
    ) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.penaltyRepository = penaltyRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public Loan execute(String loanId, DeclareDamagedRequest request) {
        Loan loan = loanRepository.findById(new LoanId(loanId))
                .orElseThrow(() -> new NoSuchElementException("Loan not found: " + loanId));

        Copy copy = copyRepository.findById(new CopyId(loan.getCopyId()))
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + loan.getCopyId()));

        // L'exemplaire revient physiquement mais est endommagé : l'emprunt se termine
        loan.markAsDamagedReturn(LocalDateTime.now());
        loanRepository.save(loan);

        // La copie va en réparation, pas disponible pour la file d'attente
        copy.updateStatus(CopyStatus.DAMAGED);
        copyRepository.save(copy);

        Penalty penalty = Penalty.create(
                new PenaltyId(idGenerator.generate()),
                loan.getUserId(),
                PenaltyType.DAMAGED_COPY,
                request.repairAmount(),
                "Copy declared damaged for loan " + loanId
        );
        penaltyRepository.save(penalty);

        return loan;
    }
}
