package com.example.printemps.users.application.models;

import com.example.printemps.users.domain.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(
        @NotNull Status status
) {
}
