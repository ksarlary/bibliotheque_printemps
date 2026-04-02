package com.example.printemps.loan.infrastructure.rest.mapper;

import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.infrastructure.rest.dto.LoanDTO;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDTO toDto(Loan loan) {
        return new LoanDTO(
                loan.getId().value(),
                loan.getCopyId(),
                loan.getUserId(),
                loan.getStartAt().toString(),
                loan.getDueAt().toString(),
                loan.getReturnedAt() != null ? loan.getReturnedAt().toString() : null,
                loan.getRenewCount(),
                loan.getStatus().name()
        );
    }
}