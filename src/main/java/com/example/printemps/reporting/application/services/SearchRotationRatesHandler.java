package com.example.printemps.reporting.application.services;

import com.example.printemps.reporting.application.gateways.ReportingRepository;
import com.example.printemps.reporting.application.models.RotationRateReport;
import com.example.printemps.reporting.application.usecases.SearchRotationRates;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchRotationRatesHandler implements SearchRotationRates {

    private final ReportingRepository reportingRepository;

    SearchRotationRatesHandler(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    @Override
    public List<RotationRateReport> handle(int limit) {
        int safeLimit = Math.max(limit, 1);
        return reportingRepository.findRotationRates(safeLimit);
    }
}