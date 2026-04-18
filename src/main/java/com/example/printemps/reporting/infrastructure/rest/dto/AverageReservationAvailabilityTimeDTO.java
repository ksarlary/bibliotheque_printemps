package com.example.printemps.reporting.infrastructure.rest.dto;

public record AverageReservationAvailabilityTimeDTO(
        long readyReservationsCount,
        double averageHours
) {
}
