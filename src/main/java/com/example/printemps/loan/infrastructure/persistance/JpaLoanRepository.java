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
        LoanEntity entity = new LoanEntity(
                loan.getId(),
                loan.getCopyId(),
                loan.getUserId(),
                loan.getStartAt(),
                loan.getDueAt(),
                loan.getReturnedAt(),
                loan.getRenewCount(),
                loan.getStatus()
        );

        LoanEntity saved = repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<Loan> findById(LoanId id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findActiveByUserId(String userId) {
        return repository.findByUserIdAndStatusIn(userId, List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE))
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Loan toDomain(LoanEntity entity) {
        return Loan.restore(
                entity.getId(),
                entity.getCopyId(),
                entity.getUserId(),
                entity.getStartAt(),
                entity.getDueAt(),
                entity.getReturnedAt(),
                entity.getRenewCount(),
                entity.getStatus()
        );
    }
}