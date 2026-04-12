package com.example.printemps.reporting.infrastructure.rest.dto;

public record AcquisitionByPeriodDTO(
        String period,
        long acquisitionCount
) {
}