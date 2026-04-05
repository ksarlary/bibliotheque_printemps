package com.example.printemps.penalties.application.gateways;

import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;

import java.util.List;
import java.util.Optional;

public interface PenaltyRepository {

    void save(Penalty penalty);
    Optional<Penalty> findById(PenaltyId id);
    List<Penalty> findByUserId(String userId);
    List<Penalty> findActiveByUserId(String userId);
}
