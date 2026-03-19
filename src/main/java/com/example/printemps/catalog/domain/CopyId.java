package com.example.printemps.catalog.domain;

import jakarta.persistence.Column;

public record CopyId(

        @Column(name = "copy_id", nullable = false, unique = true, updatable = false)
        String value

) {
}