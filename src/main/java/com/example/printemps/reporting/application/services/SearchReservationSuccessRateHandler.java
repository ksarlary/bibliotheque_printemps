package com.example.printemps.reporting.application.services;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.reporting.application.models.ReservationSuccessRateReport;
import com.example.printemps.reporting.application.usecases.SearchReservationSuccessRate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchReservationSuccessRateHandler implements SearchReservationSuccessRate {

    private final HoldRepository holdRepository;

    SearchReservationSuccessRateHandler(HoldRepository holdRepository) {
        this.holdRepository = holdRepository;
    }

    @Override
    public ReservationSuccessRateReport handle() {
        long successfulReservations = holdRepository.countByStatus(HoldStatus.PICKED_UP);

        long completedReservations = holdRepository.countByStatusIn(List.of(
                HoldStatus.PICKED_UP,
                HoldStatus.EXPIRED,
                HoldStatus.CANCELLED
        ));

        double successRate;

        if (completedReservations == 0) {
            successRate = 0.0;
        } else {
            successRate = (successfulReservations * 100.0) / completedReservations;
        }
        return new ReservationSuccessRateReport(
                successfulReservations,
                completedReservations,
                successRate
        );
    }
}