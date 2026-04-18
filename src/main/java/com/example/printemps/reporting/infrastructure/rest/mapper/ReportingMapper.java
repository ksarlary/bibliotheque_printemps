package com.example.printemps.reporting.infrastructure.rest.mapper;

import com.example.printemps.reporting.application.models.*;
import com.example.printemps.reporting.infrastructure.rest.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportingMapper {

    public TopBorrowedWorkDTO toTopBorrowedWorkDTO(TopBorrowedWorkReport report) {
        return new TopBorrowedWorkDTO(
                report.workId(),
                report.title(),
                report.isbn(),
                report.loanCount()
        );
    }

    public List<TopBorrowedWorkDTO> toTopBorrowedWorkDTOList(List<TopBorrowedWorkReport> reports) {
        return reports.stream()
                .map(this::toTopBorrowedWorkDTO)
                .toList();
    }

    public OverdueByPeriodDTO toOverdueByPeriodDTO(OverdueByPeriodReport report) {
        return new OverdueByPeriodDTO(
                report.period(),
                report.overdueCount()
        );
    }

    public List<OverdueByPeriodDTO> toOverdueByPeriodDTOList(List<OverdueByPeriodReport> reports) {
        return reports.stream()
                .map(this::toOverdueByPeriodDTO)
                .toList();
    }

    public RotationRateDTO toRotationRateDTO(RotationRateReport report) {
        return new RotationRateDTO(
                report.workId(),
                report.title(),
                report.isbn(),
                report.copyCount(),
                report.loanCount(),
                report.rotationRate()
        );
    }

    public List<RotationRateDTO> toRotationRateDTOList(List<RotationRateReport> reports) {
        return reports.stream()
                .map(this::toRotationRateDTO)
                .toList();
    }

    public AcquisitionByPeriodDTO toAcquisitionByPeriodDTO(AcquisitionByPeriodReport report) {
        return new AcquisitionByPeriodDTO(
                report.period(),
                report.acquisitionCount()
        );
    }

    public List<AcquisitionByPeriodDTO> toAcquisitionByPeriodDTOList(List<AcquisitionByPeriodReport> reports) {
        return reports.stream()
                .map(this::toAcquisitionByPeriodDTO)
                .toList();
    }

    public ReservationSuccessRateDTO toReservationSuccessRateDTO(ReservationSuccessRateReport report) {
        return new ReservationSuccessRateDTO(
                report.successfulReservations(),
                report.completedReservations(),
                report.successRate()
        );
    }

    public OverdueReturnRateDTO toOverdueReturnRateDTO(OverdueReturnRateReport report) {
        return new OverdueReturnRateDTO(
                report.lateReturns(),
                report.totalReturns(),
                report.overdueReturnRate()
        );
    }

    public AverageReservationAvailabilityTimeDTO toAverageReservationAvailabilityTimeDTO(
            AverageReservationAvailabilityTimeReport report
    ) {
        return new AverageReservationAvailabilityTimeDTO(
                report.readyReservationsCount(),
                report.averageHours()
        );
    }

}