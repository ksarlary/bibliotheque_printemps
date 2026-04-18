package com.example.printemps.reporting.application.models;

public record AverageReservationAvailabilityTimeReport(
        long readyReservationsCount,
        double averageHours
) {
}
