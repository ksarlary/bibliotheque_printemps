package com.example.printemps.catalog.domain;

import jakarta.persistence.Column;

public record WorkId(

        @Column(name = "work_id", nullable = false, unique = true, updatable = false)
        String value

) {
}