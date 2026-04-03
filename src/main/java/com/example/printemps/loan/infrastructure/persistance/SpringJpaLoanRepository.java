package com.example.printemps.loan.infrastructure.persistance;

import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringJpaLoanRepository extends JpaRepository<LoanEntity, Long> {
    Optional<LoanEntity> findById(LoanId id);
    List<LoanEntity> findByUserId(String userId);
    List<LoanEntity> findByUserIdAndStatusIn(String userId, List<LoanStatus> statuses);
}