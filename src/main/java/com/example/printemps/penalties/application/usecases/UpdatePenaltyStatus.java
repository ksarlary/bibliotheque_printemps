package com.example.printemps.penalties.application.usecases;

import com.example.printemps.penalties.application.models.UpdatePenaltyStatusRequest;
import com.example.printemps.penalties.domain.PenaltyId;

public interface UpdatePenaltyStatus {
    void handle(PenaltyId id, UpdatePenaltyStatusRequest request);

}
