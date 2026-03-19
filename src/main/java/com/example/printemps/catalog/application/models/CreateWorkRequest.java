package com.example.printemps.catalog.application.models;

public record CreateWorkRequest(
        String isbn,
        String title,
        String author,
        String publisher,
        Integer publicationYear,
        String category,
        String description
) {
}