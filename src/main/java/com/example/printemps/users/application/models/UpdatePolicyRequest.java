package com.example.printemps.users.application.models;

import jakarta.validation.constraints.Min;

public record UpdatePolicyRequest(
        @Min(1) int maxLoans,
        @Min(1) int loanDurationDays,
        @Min(0) int maxRenewals
) {
}
