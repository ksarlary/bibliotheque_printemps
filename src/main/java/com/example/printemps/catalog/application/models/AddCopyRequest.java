package com.example.printemps.catalog.application.models;

public record AddCopyRequest(
        String barcode,
        String location
) {
}