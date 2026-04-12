package com.example.printemps.reporting.application.usecases;

import com.example.printemps.reporting.application.models.RotationRateReport;

import java.util.List;

public interface SearchRotationRates {
    List<RotationRateReport> handle(int limit);
}