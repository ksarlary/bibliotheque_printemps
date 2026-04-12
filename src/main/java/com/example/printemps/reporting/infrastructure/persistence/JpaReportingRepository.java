package com.example.printemps.reporting.infrastructure.persistence;

import com.example.printemps.reporting.application.gateways.ReportingRepository;
import com.example.printemps.reporting.application.models.RotationRateReport;
import com.example.printemps.reporting.application.models.TopBorrowedWorkReport;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class JpaReportingRepository implements ReportingRepository {

    private final SpringJpaReportingRepository springJpaReportingRepository;

    JpaReportingRepository(SpringJpaReportingRepository springJpaReportingRepository) {
        this.springJpaReportingRepository = springJpaReportingRepository;
    }

    @Override
    public List<TopBorrowedWorkReport> findTopBorrowedWorks(int limit) {
        return springJpaReportingRepository.findTopBorrowedWorks(PageRequest.of(0, limit))
                .stream()
                .map(row -> new TopBorrowedWorkReport(
                        row.getWorkId(),
                        row.getTitle(),
                        row.getIsbn(),
                        row.getLoanCount()
                ))
                .toList();
    }

    @Override
    public List<RotationRateReport> findRotationRates(int limit) {
        return springJpaReportingRepository.findRotationRates(PageRequest.of(0, limit))
                .stream()
                .map(row -> new RotationRateReport(
                        row.getWorkId(),
                        row.getTitle(),
                        row.getIsbn(),
                        row.getCopyCount(),
                        row.getLoanCount(),
                        row.getRotationRate()
                ))
                .toList();
    }
}