package com.example.printemps.catalog.application.models;

import java.util.List;

public record UpdateWorkRequest(
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