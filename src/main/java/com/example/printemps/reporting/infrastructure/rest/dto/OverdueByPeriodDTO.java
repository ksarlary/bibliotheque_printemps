package com.example.printemps.reporting.infrastructure.rest.dto;

public record OverdueByPeriodDTO(
        String period,
        long overdueCount
) {
}