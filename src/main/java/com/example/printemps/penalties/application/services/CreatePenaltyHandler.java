package com.example.printemps.penalties.application.services;

import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.application.models.CreatePenaltyRequest;
import com.example.printemps.penalties.application.usecases.CreatePenalty;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.shared.DomainIdGenerator;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CreatePenaltyHandler implements CreatePenalty {
    private final PenaltyRepository penaltyRepository;
    private final DomainIdGenerator idGenerator;
    private final UserRepository userRepository;

    public CreatePenaltyHandler(PenaltyRepository penaltyRepository, DomainIdGenerator idGenerator, UserRepository userRepository) {
        this.penaltyRepository = penaltyRepository;
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public PenaltyId handle(CreatePenaltyRequest request) {
        PenaltyId penaltyId = new PenaltyId(idGenerator.generate());
        Penalty penalty = Penalty.create(penaltyId, request);
        penaltyRepository.save(penalty);

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));


        return penaltyId;
    }
}