package com.example.printemps.reporting.application.gateways;

import com.example.printemps.reporting.application.models.OverdueByPeriodReport;
import com.example.printemps.reporting.application.models.TopBorrowedWorkReport;

import java.util.List;

public interface ReportingRepository {
    List<TopBorrowedWorkReport> findTopBorrowedWorks(int limit);
}