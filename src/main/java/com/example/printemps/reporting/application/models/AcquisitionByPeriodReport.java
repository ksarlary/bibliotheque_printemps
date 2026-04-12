package com.example.printemps.reporting.application.models;

public record AcquisitionByPeriodReport(
        String period,
        long acquisitionCount
) {
}