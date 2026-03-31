package com.example.printemps.catalog.infrastructure.rest.dto;

import java.util.List;

public record WorkDTO(
        String id,
        String isbn,
        String title,
        List<String> authors,
        String publisher,
        Integer publicationYear,
        String category,
        String type,
        String language,
        List<String> subjects,
        String description
) {
}