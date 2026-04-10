package com.example.printemps.reporting.infrastructure.persistence;

public interface TopBorrowedWorkProjection {
    String getWorkId();
    String getTitle();
    String getIsbn();
    Long getLoanCount();
}