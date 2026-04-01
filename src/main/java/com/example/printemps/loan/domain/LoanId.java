package com.example.printemps.loan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record LoanId(
        @Column(name = "loan_id", nullable = false, unique = true, updatable = false)
        String value
) {
}