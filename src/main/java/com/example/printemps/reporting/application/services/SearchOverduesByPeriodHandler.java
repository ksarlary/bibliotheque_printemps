package com.example.printemps.reporting.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.reporting.application.models.OverdueByPeriodReport;
import com.example.printemps.reporting.application.usecases.SearchOverduesByPeriod;
import com.example.printemps.shared.error.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
class SearchOverduesByPeriodHandler implements SearchOverduesByPeriod {

    private final LoanRepository loanRepository;

    SearchOverduesByPeriodHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public List<OverdueByPeriodReport> handle(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new BusinessException("'from' must be before or equal to 'to'");
        }

        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);
        LocalDateTime referenceDate = LocalDateTime.now();

        return loanRepository.findByDueAtBetweenOrderByDueAtAsc(fromDateTime, toDateTime)
                .stream()
                .filter(loan -> loan.isLateAt(referenceDate))
                .collect(Collectors.groupingBy(
                        loan -> YearMonth.from(loan.getDueAt()),
                        TreeMap::new,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> new OverdueByPeriodReport(
                        entry.getKey().toString(),
                        entry.getValue()
                ))
                .toList();
    }
}