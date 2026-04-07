package com.example.printemps.users.application.models;

import com.example.printemps.users.domain.LateFeeMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdatePolicyRequest(
        @Min(1) int maxLoans,
        @Min(1) int loanDurationDays,
        @Min(0) int maxRenewals,
        @Min(0) int blockAfterDaysLate,
        @NotNull LateFeeMode lateFeeMode,
        @NotNull BigDecimal lateFeeAmount
) {
}
