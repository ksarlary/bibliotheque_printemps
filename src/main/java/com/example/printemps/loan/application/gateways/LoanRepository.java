package com.example.printemps.loan.application.gateways;

import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.domain.LoanStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(LoanId id);
    List<Loan> findByUserId(String userId);
    List<Loan> findActiveByUserId(String userId);
    List<Loan> findByDueAtBetweenOrderByDueAtAsc(LocalDateTime from, LocalDateTime to);
    List<Loan> findByStatus(LoanStatus status);
}