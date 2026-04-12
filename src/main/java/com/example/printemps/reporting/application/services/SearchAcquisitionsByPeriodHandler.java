package com.example.printemps.reporting.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.reporting.application.models.AcquisitionByPeriodReport;
import com.example.printemps.reporting.application.usecases.SearchAcquisitionsByPeriod;
import com.example.printemps.shared.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
class SearchAcquisitionsByPeriodHandler implements SearchAcquisitionsByPeriod {

    private final CopyRepository copyRepository;

    SearchAcquisitionsByPeriodHandler(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcquisitionByPeriodReport> handle(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new BusinessException("'from' must be before or equal to 'to'");
        }

        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        List<Copy> copies = copyRepository.findByAcquiredAtBetween(fromDateTime, toDateTime);

        return copies.stream()
                .collect(Collectors.groupingBy(
                        copy -> YearMonth.from(copy.getAcquiredAt()),
                        TreeMap::new,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> new AcquisitionByPeriodReport(
                        entry.getKey().toString(),
                        entry.getValue()
                ))
                .toList();
    }
}