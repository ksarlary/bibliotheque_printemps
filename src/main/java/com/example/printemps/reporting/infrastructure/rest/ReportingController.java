package com.example.printemps.reporting.infrastructure.rest;

import com.example.printemps.reporting.application.models.*;
import com.example.printemps.reporting.application.usecases.*;
import com.example.printemps.reporting.infrastructure.rest.dto.*;
import com.example.printemps.reporting.infrastructure.rest.mapper.ReportingMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reporting")
class ReportingController {

    private final SearchTopBorrowedWorks searchTopBorrowedWorks;
    private final SearchOverduesByPeriod searchOverduesByPeriod;
    private final SearchRotationRates searchRotationRates;
    private final SearchAcquisitionsByPeriod searchAcquisitionsByPeriod;
    private final ReportingMapper reportingMapper;
    private final ReportingCsvExporter reportingCsvExporter;
    private final SearchReservationSuccessRate searchReservationSuccessRate;
    private final SearchOverdueReturnRate searchOverdueReturnRate;

    ReportingController(
            SearchTopBorrowedWorks searchTopBorrowedWorks,
            SearchOverduesByPeriod searchOverduesByPeriod,
            SearchRotationRates searchRotationRates,
            SearchAcquisitionsByPeriod searchAcquisitionsByPeriod,
            ReportingMapper reportingMapper,
            ReportingCsvExporter reportingCsvExporter, SearchReservationSuccessRate searchReservationSuccessRate, SearchOverdueReturnRate searchOverdueReturnRate
    ) {
        this.searchTopBorrowedWorks = searchTopBorrowedWorks;
        this.searchOverduesByPeriod = searchOverduesByPeriod;
        this.searchRotationRates = searchRotationRates;
        this.searchAcquisitionsByPeriod = searchAcquisitionsByPeriod;
        this.reportingMapper = reportingMapper;
        this.reportingCsvExporter = reportingCsvExporter;
        this.searchReservationSuccessRate = searchReservationSuccessRate;
        this.searchOverdueReturnRate = searchOverdueReturnRate;
    }

    @GetMapping("/top-borrowed-works")
    ResponseEntity<List<TopBorrowedWorkDTO>> getTopBorrowedWorks(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<TopBorrowedWorkReport> reports = searchTopBorrowedWorks.handle(limit);
        List<TopBorrowedWorkDTO> dtos = reportingMapper.toTopBorrowedWorkDTOList(reports);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/top-borrowed-works/export", produces = "text/csv")
    ResponseEntity<String> exportTopBorrowedWorksCsv(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<TopBorrowedWorkReport> reports = searchTopBorrowedWorks.handle(limit);
        List<TopBorrowedWorkDTO> dtos = reportingMapper.toTopBorrowedWorkDTOList(reports);
        String csv = reportingCsvExporter.exportTopBorrowedWorks(dtos);

        return buildCsvResponse("top-borrowed-works.csv", csv);
    }

    @GetMapping("/overdues-by-period")
    ResponseEntity<List<OverdueByPeriodDTO>> getOverduesByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<OverdueByPeriodReport> reports = searchOverduesByPeriod.handle(from, to);
        List<OverdueByPeriodDTO> dtos = reportingMapper.toOverdueByPeriodDTOList(reports);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/overdues-by-period/export", produces = "text/csv")
    ResponseEntity<String> exportOverduesByPeriodCsv(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<OverdueByPeriodReport> reports = searchOverduesByPeriod.handle(from, to);
        List<OverdueByPeriodDTO> dtos = reportingMapper.toOverdueByPeriodDTOList(reports);
        String csv = reportingCsvExporter.exportOverduesByPeriod(dtos);

        return buildCsvResponse("overdues-by-period-" + from + "-to-" + to + ".csv", csv);
    }

    @GetMapping("/rotation-rates")
    ResponseEntity<List<RotationRateDTO>> getRotationRates(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<RotationRateReport> reports = searchRotationRates.handle(limit);
        List<RotationRateDTO> dtos = reportingMapper.toRotationRateDTOList(reports);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/rotation-rates/export", produces = "text/csv")
    ResponseEntity<String> exportRotationRatesCsv(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<RotationRateReport> reports = searchRotationRates.handle(limit);
        List<RotationRateDTO> dtos = reportingMapper.toRotationRateDTOList(reports);
        String csv = reportingCsvExporter.exportRotationRates(dtos);

        return buildCsvResponse("rotation-rates.csv", csv);
    }

    @GetMapping("/acquisitions-by-period")
    ResponseEntity<List<AcquisitionByPeriodDTO>> getAcquisitionsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<AcquisitionByPeriodReport> reports = searchAcquisitionsByPeriod.handle(from, to);
        List<AcquisitionByPeriodDTO> dtos = reportingMapper.toAcquisitionByPeriodDTOList(reports);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/acquisitions-by-period/export", produces = "text/csv")
    ResponseEntity<String> exportAcquisitionsByPeriodCsv(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<AcquisitionByPeriodReport> reports = searchAcquisitionsByPeriod.handle(from, to);
        List<AcquisitionByPeriodDTO> dtos = reportingMapper.toAcquisitionByPeriodDTOList(reports);
        String csv = reportingCsvExporter.exportAcquisitionsByPeriod(dtos);

        return buildCsvResponse("acquisitions-by-period-" + from + "-to-" + to + ".csv", csv);
    }

    private ResponseEntity<String> buildCsvResponse(String fileName, String csvContent) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("text/csv;charset=UTF-8"))
                .body(csvContent);
    }

    @GetMapping("/kpis/reservation-success-rate")
    ResponseEntity<ReservationSuccessRateDTO> getReservationSuccessRate() {
        ReservationSuccessRateReport report = searchReservationSuccessRate.handle();
        ReservationSuccessRateDTO dto = reportingMapper.toReservationSuccessRateDTO(report);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/kpis/overdue-return-rate")
    ResponseEntity<OverdueReturnRateDTO> getOverdueReturnRate() {
        OverdueReturnRateReport report = searchOverdueReturnRate.handle();
        OverdueReturnRateDTO dto = reportingMapper.toOverdueReturnRateDTO(report);
        return ResponseEntity.ok(dto);
    }
}