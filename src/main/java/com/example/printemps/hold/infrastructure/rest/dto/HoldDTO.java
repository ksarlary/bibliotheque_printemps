package com.example.printemps.hold.infrastructure.rest.dto;

import java.time.LocalDateTime;

public record HoldDTO(
        String id,
        String workId,
        String copyId,
        String userId,
        String status,
        int queuePosition,
        LocalDateTime pickupUntil
) {
}
