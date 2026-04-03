package com.example.printemps.catalog.application.models;

public record SearchWorksQuery(
        String keyword,
        String type,
        String language,
        String subject,
        Boolean availableOnly,
        Integer year
) {
}
