package com.example.printemps.reporting.application.models;

public record TopBorrowedWorkReport(
        String workId,
        String title,
        String isbn,
        long loanCount
) {
}