package com.example.printemps.reporting.application.usecases;

import com.example.printemps.reporting.application.models.AcquisitionByPeriodReport;

import java.time.LocalDate;
import java.util.List;

public interface SearchAcquisitionsByPeriod {
    List<AcquisitionByPeriodReport> handle(LocalDate from, LocalDate to);
}