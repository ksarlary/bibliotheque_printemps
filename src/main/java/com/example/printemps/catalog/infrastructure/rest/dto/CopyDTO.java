package com.example.printemps.catalog.infrastructure.rest.dto;

public record CopyDTO(
        String id,
        String workId,
        String barcode,
        String status,
        String location
) {
}
