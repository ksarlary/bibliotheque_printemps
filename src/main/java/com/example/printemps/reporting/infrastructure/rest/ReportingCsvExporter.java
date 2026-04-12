package com.example.printemps.reporting.infrastructure.rest;

import com.example.printemps.reporting.infrastructure.rest.dto.AcquisitionByPeriodDTO;
import com.example.printemps.reporting.infrastructure.rest.dto.OverdueByPeriodDTO;
import com.example.printemps.reporting.infrastructure.rest.dto.RotationRateDTO;
import com.example.printemps.reporting.infrastructure.rest.dto.TopBorrowedWorkDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ReportingCsvExporter {

    String exportTopBorrowedWorks(List<TopBorrowedWorkDTO> rows) {
        StringBuilder csv = new StringBuilder();
        csv.append("workId,title,isbn,loanCount\n");

        for (TopBorrowedWorkDTO row : rows) {
            appendLine(csv, row.workId(), row.title(), row.isbn(), row.loanCount());

        }
        return csv.toString();
    }

    String exportOverduesByPeriod(List<OverdueByPeriodDTO> rows) {
        StringBuilder csv = new StringBuilder();
        csv.append("period,overdueCount\n");

        for (OverdueByPeriodDTO row : rows) {
            appendLine(csv, row.period(), row.overdueCount());
        }

        return csv.toString();
    }

    String exportRotationRates(List<RotationRateDTO> rows) {
        StringBuilder csv = new StringBuilder();
        csv.append("workId,title,isbn,copyCount,loanCount,rotationRate\n");

        for (RotationRateDTO row : rows) {
            appendLine(csv,
                    row.workId(),
                    row.title(),
                    row.isbn(),
                    row.copyCount(),
                    row.loanCount(),
                    row.rotationRate()
            );
        }

        return csv.toString();
    }

    String exportAcquisitionsByPeriod(List<AcquisitionByPeriodDTO> rows) {
        StringBuilder csv = new StringBuilder();
        csv.append("period,acquisitionCount\n");

        for (AcquisitionByPeriodDTO row : rows) {
            appendLine(csv, row.period(), row.acquisitionCount()
            );
        }

        return csv.toString();
    }

    private void appendLine(StringBuilder csv, Object... values) {
        for (int i = 0; i < values.length; i++) {
            csv.append(escape(values[i]));

            if (i < values.length - 1) {
                csv.append(",");
            }
        }
        csv.append("\n");
    }

    private String escape(Object value) {
        if (value == null) {
            return "";
        }

        String text = String.valueOf(value);

        boolean mustBeQuoted = text.contains(",")
                || text.contains("\"")
                || text.contains("\n")
                || text.contains("\r");

        if (!mustBeQuoted) {
            return text;
        }

        return "\"" + text.replace("\"", "\"\"") + "\"";
    }



}
