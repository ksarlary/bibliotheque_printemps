package com.example.printemps.reporting.application.usecases;

import com.example.printemps.reporting.application.models.OverdueByPeriodReport;

import java.time.LocalDate;
import java.util.List;

public interface SearchOverduesByPeriod {
    List<OverdueByPeriodReport> handle(LocalDate from, LocalDate to);

}