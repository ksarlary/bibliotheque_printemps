package com.example.printemps.penalties.application.services;

import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.application.models.UpdatePenaltyStatusRequest;
import com.example.printemps.penalties.application.usecases.UpdatePenaltyStatus;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UpdatePenaltyStatusHandler implements UpdatePenaltyStatus {

    private final PenaltyRepository penaltyRepository;

    UpdatePenaltyStatusHandler(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }

    @Override
    @Transactional
    public void handle(PenaltyId id, UpdatePenaltyStatusRequest request) {
        Penalty penalty = penaltyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Penalty not found"));
        penalty.updateStatus(request.status());

    }
}
