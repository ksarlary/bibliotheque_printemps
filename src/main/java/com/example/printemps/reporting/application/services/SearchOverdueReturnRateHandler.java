package com.example.printemps.reporting.application.services;

import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanStatus;
import com.example.printemps.reporting.application.models.OverdueReturnRateReport;
import com.example.printemps.reporting.application.usecases.SearchOverdueReturnRate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchOverdueReturnRateHandler implements SearchOverdueReturnRate {

    private final LoanRepository loanRepository;

    SearchOverdueReturnRateHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public OverdueReturnRateReport handle() {
        List<Loan> returnedLoans = loanRepository.findByStatus(LoanStatus.RETURNED);

        long totalReturns = returnedLoans.size();

        long lateReturns = returnedLoans.stream()
                .filter(loan -> loan.getReturnedAt() != null)
                .filter(loan -> loan.isLateAt(loan.getReturnedAt()))
                .count();

        double overdueReturnRate;

        if (totalReturns == 0) {
            overdueReturnRate = 0.0;
        } else {
            overdueReturnRate = (lateReturns * 100.0) / totalReturns;
        }

        return new OverdueReturnRateReport(
                lateReturns,
                totalReturns,
                overdueReturnRate
        );
    }
}