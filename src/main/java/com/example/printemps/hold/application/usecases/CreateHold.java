package com.example.printemps.hold.application.usecases;

import com.example.printemps.hold.application.models.CreateHoldRequest;
import com.example.printemps.hold.domain.HoldId;

public interface CreateHold {
    HoldId handle(CreateHoldRequest request);
}