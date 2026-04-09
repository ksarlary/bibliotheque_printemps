package com.example.printemps.reporting.application.services;

import com.example.printemps.reporting.application.gateways.ReportingRepository;
import com.example.printemps.reporting.application.usecases.SearchTopBorrowedWorks;
import com.example.printemps.reporting.domain.TopBorrowedWorkReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchTopBorrowedWorksHandler implements SearchTopBorrowedWorks {

    private final ReportingRepository reportingRepository;

    SearchTopBorrowedWorksHandler(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    @Override
    public List<TopBorrowedWorkReport> handle(int limit) {
        int safeLimit = Math.max(limit, 1);

        return reportingRepository.findTopBorrowedWorks()
                .stream()
                .limit(safeLimit)
                .toList();
    }
}