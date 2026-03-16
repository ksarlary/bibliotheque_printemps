package com.example.printemps.users.application.models;

import com.example.printemps.users.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank String ssoId,
        @NotNull Category category
) {
}