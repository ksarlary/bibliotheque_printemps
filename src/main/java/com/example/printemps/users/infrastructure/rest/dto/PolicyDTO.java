package com.example.printemps.users.infrastructure.rest.dto;

public record PolicyDTO(
        String category,
        int maxLoans,
        int loanDurationDays,
        int maxRenewals
) {
}
