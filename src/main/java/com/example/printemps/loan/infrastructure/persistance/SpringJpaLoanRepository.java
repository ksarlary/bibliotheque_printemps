package com.example.printemps.loan.infrastructure.persistance;

import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringJpaLoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findById(LoanId id);
    List<Loan> findByUserId(String userId);
    List<Loan> findByUserIdAndStatusIn(String userId, List<LoanStatus> statuses);
}