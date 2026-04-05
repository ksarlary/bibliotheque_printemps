package com.example.printemps.hold.domain;

public enum HoldStatus {
    REQUESTED,
    QUEUED,
    READY_FOR_PICKUP,
    PICKED_UP,
    EXPIRED,
    CANCELLED
}
