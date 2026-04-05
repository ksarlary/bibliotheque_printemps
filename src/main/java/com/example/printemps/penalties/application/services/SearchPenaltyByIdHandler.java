package com.example.printemps.penalties.application.services;

import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.application.usecases.SearchPenaltyById;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class SearchPenaltyByIdHandler implements SearchPenaltyById {
    private final PenaltyRepository penaltyRepository;


    SearchPenaltyByIdHandler(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Penalty> handle(PenaltyId id) {
        return penaltyRepository.findById(id);
    }
}
