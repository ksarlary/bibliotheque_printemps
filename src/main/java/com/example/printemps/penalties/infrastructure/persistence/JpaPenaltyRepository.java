package com.example.printemps.penalties.infrastructure.persistence;

import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.penalties.domain.PenaltyStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JpaPenaltyRepository implements PenaltyRepository {

    private final SpringJpaPenaltyRepository jpaPenaltyRepository;

    JpaPenaltyRepository(SpringJpaPenaltyRepository jpaPenaltyRepository) {
        this.jpaPenaltyRepository = jpaPenaltyRepository;
    }


    @Override
    public void save(Penalty penalty) {
        jpaPenaltyRepository.save(penalty);

    }

    @Override
    public Optional<Penalty> findById(PenaltyId id) {
        return jpaPenaltyRepository.findById(id);
    }

    @Override
    public List<Penalty> findByUserId(String userId) {
        return jpaPenaltyRepository.findByUserId(userId);
    }

    @Override
    public List<Penalty> findActiveByUserId(String userId) {
        return jpaPenaltyRepository.findByUserIdAndStatus(userId, PenaltyStatus.ACTIVE);
    }
}
