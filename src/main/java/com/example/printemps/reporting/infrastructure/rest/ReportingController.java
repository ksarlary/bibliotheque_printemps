package com.example.printemps.reporting.infrastructure.rest;

import com.example.printemps.reporting.application.models.OverdueByPeriodReport;
import com.example.printemps.reporting.application.models.TopBorrowedWorkReport;
import com.example.printemps.reporting.application.usecases.SearchOverduesByPeriod;
import com.example.printemps.reporting.application.usecases.SearchTopBorrowedWorks;
import com.example.printemps.reporting.infrastructure.rest.dto.OverdueByPeriodDTO;
import com.example.printemps.reporting.infrastructure.rest.dto.TopBorrowedWorkDTO;
import com.example.printemps.reporting.infrastructure.rest.mapper.ReportingMapper;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final ReportingMapper reportingMapper;
    private final SearchOverduesByPeriod searchOverduesByPeriod;


    ReportingController(
            SearchTopBorrowedWorks searchTopBorrowedWorks,
            ReportingMapper reportingMapper, SearchOverduesByPeriod searchOverduesByPeriod
    ) {
        this.searchTopBorrowedWorks = searchTopBorrowedWorks;
        this.reportingMapper = reportingMapper;
        this.searchOverduesByPeriod = searchOverduesByPeriod;
    }

    @GetMapping("/top-borrowed-works")
    ResponseEntity<List<TopBorrowedWorkDTO>> getTopBorrowedWorks(
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<TopBorrowedWorkReport> reports = searchTopBorrowedWorks.handle(limit);
        List<TopBorrowedWorkDTO> dtos = reportingMapper.toTopBorrowedWorkDTOList(reports);
        return ResponseEntity.ok(dtos);
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

}