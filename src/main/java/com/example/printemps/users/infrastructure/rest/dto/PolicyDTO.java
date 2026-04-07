package com.example.printemps.users.infrastructure.rest.dto;

import java.math.BigDecimal;

public record PolicyDTO(
        String category,
        int maxLoans,
        int loanDurationDays,
        int maxRenewals,
        int blockAfterDaysLate,
        String lateFeeMode,
        BigDecimal lateFeeAmount
) {
}
