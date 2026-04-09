package com.example.printemps.reporting.infrastructure.rest.dto;

public record TopBorrowedWorkDTO(
        String workId,
        String title,
        String isbn,
        long loanCount
) {
}