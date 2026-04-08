package com.example.printemps.loan.infrastructure.persistance;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.domain.LoanStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaLoanRepository implements LoanRepository {

    private final SpringJpaLoanRepository repository;

    public JpaLoanRepository(SpringJpaLoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }

    @Override
    public Optional<Loan> findById(LoanId id) {
        return repository.findById(id);
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return repository.findByUserId(userId);

    }

    @Override
    public List<Loan> findActiveByUserId(String userId) {
        return repository.findByUserIdAndStatusIn(userId, List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE));

    }

}