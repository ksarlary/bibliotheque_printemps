package com.example.printemps.reporting.infrastructure.rest.dto;

public record RotationRateDTO(
        String workId,
        String title,
        String isbn,
        long copyCount,
        long loanCount,
        double rotationRate
) {
}