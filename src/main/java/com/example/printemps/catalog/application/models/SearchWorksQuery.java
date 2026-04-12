package com.example.printemps.catalog.application.models;

public record SearchWorksQuery(
        String keyword,
        String isbn,
        String type,
        String language,
        String subject,
        Boolean availableOnly,
        Integer year,
        String location
) {
}
