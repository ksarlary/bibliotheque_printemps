package com.example.printemps.penalties.domain;

import jakarta.persistence.Column;

public record PenaltyId(

        @Column(name = "penalty_id", nullable = false, unique = true, updatable = false)
        String value

) {
}