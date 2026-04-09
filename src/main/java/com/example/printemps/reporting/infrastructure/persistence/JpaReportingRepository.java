package com.example.printemps.reporting.infrastructure.persistence;

import com.example.printemps.reporting.application.gateways.ReportingRepository;
import com.example.printemps.reporting.domain.TopBorrowedWorkReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class JpaReportingRepository implements ReportingRepository {

    private final SpringJpaReportingRepository springJpaReportingRepository;

    JpaReportingRepository(SpringJpaReportingRepository springJpaReportingRepository) {
        this.springJpaReportingRepository = springJpaReportingRepository;
    }

    @Override
    public List<TopBorrowedWorkReport> findTopBorrowedWorks() {
        return springJpaReportingRepository.findTopBorrowedWorks()
                .stream()
                .map(row -> new TopBorrowedWorkReport(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        ((Number) row[3]).longValue()
                ))
                .toList();
    }
}