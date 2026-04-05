package com.example.printemps.penalties.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PenaltyDTO(
        String id,
        String userId,
        String type,
        BigDecimal amount,
        String reason,
        String status,
        LocalDateTime createdAt

) {
}
