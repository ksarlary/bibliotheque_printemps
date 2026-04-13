package com.example.printemps.reporting.infrastructure.rest.dto;

public record OverdueReturnRateDTO(
        long lateReturns,
        long totalReturns,
        double overdueReturnRate
) {
}