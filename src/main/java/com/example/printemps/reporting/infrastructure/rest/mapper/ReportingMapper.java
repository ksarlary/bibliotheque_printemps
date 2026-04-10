package com.example.printemps.reporting.infrastructure.rest.mapper;

import com.example.printemps.reporting.application.models.OverdueByPeriodReport;
import com.example.printemps.reporting.application.models.TopBorrowedWorkReport;
import com.example.printemps.reporting.infrastructure.rest.dto.OverdueByPeriodDTO;
import com.example.printemps.reporting.infrastructure.rest.dto.TopBorrowedWorkDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportingMapper {

    public TopBorrowedWorkDTO toTopBorrowedWorkDTO(TopBorrowedWorkReport report) {
        return new TopBorrowedWorkDTO(
                report.workId(),
                report.title(),
                report.isbn(),
                report.loanCount()
        );
    }

    public List<TopBorrowedWorkDTO> toTopBorrowedWorkDTOList(List<TopBorrowedWorkReport> reports) {
        return reports.stream()
                .map(this::toTopBorrowedWorkDTO)
                .toList();
    }

    public OverdueByPeriodDTO toOverdueByPeriodDTO(OverdueByPeriodReport report) {
        return new OverdueByPeriodDTO(
                report.period(),
                report.overdueCount()
        );
    }

    public List<OverdueByPeriodDTO> toOverdueByPeriodDTOList(List<OverdueByPeriodReport> reports) {
        return reports.stream()
                .map(this::toOverdueByPeriodDTO)
                .toList();
    }
}