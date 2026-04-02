package com.example.printemps.loan.infrastructure.rest.dto;

public record LoanDTO(
        String id,
        String copyId,
        String userId,
        String startAt,
        String dueAt,
        String returnedAt,
        Integer renewCount,
        String status
) {
}