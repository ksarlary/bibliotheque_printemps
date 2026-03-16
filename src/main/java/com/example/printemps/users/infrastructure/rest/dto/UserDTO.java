package com.example.printemps.users.infrastructure.rest.dto;

public record UserDTO(
        String ssoId,
        String category,
        String status
) {
}