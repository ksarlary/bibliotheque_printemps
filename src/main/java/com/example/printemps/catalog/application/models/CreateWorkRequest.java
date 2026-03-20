package com.example.printemps.catalog.application.models;

public record CreateWorkRequest(
        String isbn,
        String title,
        String authors,
        String publisher,
        Integer publicationYear,
        String category,
        String type,
        String language,
        String subject,
        String description
) {
}