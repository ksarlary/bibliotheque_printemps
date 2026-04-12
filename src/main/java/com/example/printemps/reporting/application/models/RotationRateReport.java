package com.example.printemps.reporting.application.models;

public record RotationRateReport(
        String workId,
        String title,
        String isbn,
        long copyCount,
        long loanCount,
        double rotationRate
) {
}