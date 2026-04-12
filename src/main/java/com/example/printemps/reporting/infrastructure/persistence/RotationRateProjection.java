package com.example.printemps.reporting.infrastructure.persistence;

public interface RotationRateProjection {
    String getWorkId();
    String getTitle();
    String getIsbn();
    Long getCopyCount();
    Long getLoanCount();
    Double getRotationRate();
}