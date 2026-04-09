package com.example.printemps.reporting.domain;

public record TopBorrowedWorkReport(
        String workId,
        String title,
        String isbn,
        long loanCount
) {
}