package com.example.printemps.reporting.application.models;

public record OverdueByPeriodReport(
        String period,
        long overdueCount
) {
}
