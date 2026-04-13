package com.example.printemps.reporting.application.models;

public record ReservationSuccessRateReport(
        long successfulReservations,
        long completedReservations,
        double successRate
) {
}