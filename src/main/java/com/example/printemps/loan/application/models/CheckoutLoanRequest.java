package com.example.printemps.loan.application.models;

public record CheckoutLoanRequest(
        String copyId,
        String userId
) {
}