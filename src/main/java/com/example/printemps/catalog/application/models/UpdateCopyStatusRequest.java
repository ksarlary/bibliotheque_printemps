package com.example.printemps.catalog.application.models;

import com.example.printemps.catalog.domain.CopyStatus;

public record UpdateCopyStatusRequest(
        CopyStatus status
) {
}