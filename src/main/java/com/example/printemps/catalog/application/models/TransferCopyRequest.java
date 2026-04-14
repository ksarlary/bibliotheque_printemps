package com.example.printemps.catalog.application.models;

import jakarta.validation.constraints.NotBlank;

public record TransferCopyRequest(
        @NotBlank String targetLocation
) {
}
