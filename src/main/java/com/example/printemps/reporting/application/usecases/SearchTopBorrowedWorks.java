package com.example.printemps.reporting.application.usecases;

import com.example.printemps.reporting.application.models.TopBorrowedWorkReport;

import java.util.List;

public interface SearchTopBorrowedWorks {
    List<TopBorrowedWorkReport> handle(int limit);
}