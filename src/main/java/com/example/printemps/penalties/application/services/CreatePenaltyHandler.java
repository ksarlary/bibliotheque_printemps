package com.example.printemps.penalties.application.services;

import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.application.models.CreatePenaltyRequest;
import com.example.printemps.penalties.application.usecases.CreatePenalty;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.shared.DomainIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreatePenaltyHandler implements CreatePenalty {
    private final PenaltyRepository penaltyRepository;
    private final DomainIdGenerator idGenerator;

    public CreatePenaltyHandler(PenaltyRepository penaltyRepository, DomainIdGenerator idGenerator) {
        this.penaltyRepository = penaltyRepository;
        this.idGenerator = idGenerator;
    }


    @Override
    @Transactional
    public PenaltyId handle(CreatePenaltyRequest request) {
        PenaltyId penaltyId = new PenaltyId(idGenerator.generate());
        Penalty penalty = Penalty.create(penaltyId, request);
        penaltyRepository.save(penalty);
        return penaltyId;


    }
}
