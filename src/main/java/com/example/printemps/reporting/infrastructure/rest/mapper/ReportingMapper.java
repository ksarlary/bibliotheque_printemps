package com.example.printemps.reporting.infrastructure.rest.mapper;

import com.example.printemps.reporting.domain.TopBorrowedWorkReport;
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
}