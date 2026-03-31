package com.example.printemps.penalties.application.models;

import com.example.printemps.penalties.domain.PenaltyStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePenaltyStatusRequest(
        @NotNull PenaltyStatus status
) {
}