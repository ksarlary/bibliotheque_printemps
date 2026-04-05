package com.example.printemps.shared.error;

public record ValidationError(
        String field,
        Object rejectedValue,
        String message
) {
}
