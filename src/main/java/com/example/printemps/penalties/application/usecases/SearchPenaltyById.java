package com.example.printemps.penalties.application.usecases;

import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;

import java.util.Optional;

public interface SearchPenaltyById {
    Optional<Penalty> handle(PenaltyId id);
}
