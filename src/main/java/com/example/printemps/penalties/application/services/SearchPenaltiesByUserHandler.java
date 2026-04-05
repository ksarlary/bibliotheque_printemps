package com.example.printemps.penalties.application.services;

import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.application.usecases.SearchPenaltiesByUser;
import com.example.printemps.penalties.domain.Penalty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchPenaltiesByUserHandler implements SearchPenaltiesByUser {
    private final PenaltyRepository penaltyRepository;

    SearchPenaltiesByUserHandler(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }


    @Override
    public List<Penalty> handle(String userId) {
        return penaltyRepository.findByUserId(userId);
    }
}
