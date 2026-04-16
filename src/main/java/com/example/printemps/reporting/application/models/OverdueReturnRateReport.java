package com.example.printemps.reporting.application.models;

public record OverdueReturnRateReport(
        long lateReturns,
        long totalReturns,
        double overdueReturnRate
) {
}