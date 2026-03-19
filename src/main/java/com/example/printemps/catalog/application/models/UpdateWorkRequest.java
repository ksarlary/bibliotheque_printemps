package com.example.printemps.catalog.application.models;

public record UpdateWorkRequest(
        Long workId,
        String title,
        String author,
        String publisher,
        Integer publicationYear,
        String category,
        String description
) {
}