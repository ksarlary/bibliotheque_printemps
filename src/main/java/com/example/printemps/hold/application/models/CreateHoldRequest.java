package com.example.printemps.hold.application.models;

import jakarta.validation.constraints.NotBlank;

public record CreateHoldRequest(
        @NotBlank String workId,
        @NotBlank String userId
) {
}
