package com.example.printemps.catalog.infrastructure.rest.dto;

public record WorkDTO(
        String id,
        String isbn,
        String title,
        String authors,
        String publisher,
        Integer publicationYear,
        String category,
        String language,
        String description
) {
}