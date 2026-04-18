package com.example.printemps.reporting.application.services;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.reporting.application.models.AverageReservationAvailabilityTimeReport;
import com.example.printemps.reporting.application.usecases.SearchAverageReservationAvailabilityTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
class SearchAverageReservationAvailabilityTimeHandler implements SearchAverageReservationAvailabilityTime {

    private final HoldRepository holdRepository;

    SearchAverageReservationAvailabilityTimeHandler(HoldRepository holdRepository) {
        this.holdRepository = holdRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AverageReservationAvailabilityTimeReport handle() {
        List<HoldStatus> statuses = List.of(
                HoldStatus.READY_FOR_PICKUP,
                HoldStatus.PICKED_UP,
                HoldStatus.EXPIRED
        );

        List<Double> durations = holdRepository.findByStatusIn(statuses)
                .stream()
                .map(hold -> Duration.between(
                        hold.getRequestedAt(),
                        hold.getReadyForPickupAt()
                ).toMinutes() / 60.0)
                .toList();

        double averageHours = durations.isEmpty()
                ? 0.0
                : durations.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        return new AverageReservationAvailabilityTimeReport(
                durations.size(),
                averageHours
        );
    }
}