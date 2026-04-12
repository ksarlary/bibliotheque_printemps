package com.example.printemps.catalog.infrastructure.rest.dto;

import java.time.LocalDateTime;

public record CopyDTO(
        String id,
        String workId,
        String barcode,
        String status,
        String location,
        LocalDateTime acquiredAt
) {
}
