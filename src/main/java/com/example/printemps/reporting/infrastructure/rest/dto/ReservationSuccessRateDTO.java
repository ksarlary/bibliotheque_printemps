package com.example.printemps.reporting.infrastructure.rest.dto;

public record ReservationSuccessRateDTO(
        long successfulReservations,
        long completedReservations,
        double successRate
) {
}