package com.example.printemps.penalties.application.models;

import com.example.printemps.penalties.domain.PenaltyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record CreatePenaltyRequest(
        @NotBlank String userId,
        @NotNull PenaltyType type,
        @NotNull BigDecimal amount,
        @NotBlank String reason
) {
}