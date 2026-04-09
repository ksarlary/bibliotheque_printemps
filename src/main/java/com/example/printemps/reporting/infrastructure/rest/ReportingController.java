package com.example.printemps.reporting.infrastructure.rest;

import com.example.printemps.reporting.application.usecases.SearchTopBorrowedWorks;
import com.example.printemps.reporting.infrastructure.rest.dto.TopBorrowedWorkDTO;
import com.example.printemps.reporting.infrastructure.rest.mapper.ReportingMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reporting")
class ReportingController {

    private final SearchTopBorrowedWorks searchTopBorrowedWorks;
    private final ReportingMapper reportingMapper;

    ReportingController(
            SearchTopBorrowedWorks searchTopBorrowedWorks,
            ReportingMapper reportingMapper
    ) {
        this.searchTopBorrowedWorks = searchTopBorrowedWorks;
        this.reportingMapper = reportingMapper;
    }

    @GetMapping("/top-borrowed-works")
    ResponseEntity<List<TopBorrowedWorkDTO>> getTopBorrowedWorks(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
                reportingMapper.toTopBorrowedWorkDTOList(
                        searchTopBorrowedWorks.handle(limit)
                )
        );
    }
}