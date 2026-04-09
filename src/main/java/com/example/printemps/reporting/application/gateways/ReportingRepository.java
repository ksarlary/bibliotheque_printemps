package com.example.printemps.reporting.application.gateways;

import com.example.printemps.reporting.domain.TopBorrowedWorkReport;

import java.util.List;

public interface ReportingRepository {
    List<TopBorrowedWorkReport> findTopBorrowedWorks();
}