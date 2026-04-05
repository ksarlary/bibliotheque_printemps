package com.example.printemps.penalties.application.usecases;

import com.example.printemps.penalties.application.models.CreatePenaltyRequest;
import com.example.printemps.penalties.domain.PenaltyId;

public interface CreatePenalty {
    PenaltyId handle(CreatePenaltyRequest request);
}
