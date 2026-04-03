package com.example.printemps.catalog.infrastructure.rest.dto;

import java.util.List;

public record WorkDetailDTO(
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
        String description,
        List<CopyDTO> copies,
        List<WorkDTO> similarWorks
) {
}
