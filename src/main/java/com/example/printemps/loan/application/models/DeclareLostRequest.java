package com.example.printemps.loan.application.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DeclareLostRequest(
        @NotNull @DecimalMin("0.0") BigDecimal replacementAmount
) {
}
