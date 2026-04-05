package com.example.printemps.hold.infrastructure.persistence;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldId;
import com.example.printemps.hold.domain.HoldStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaHoldRepository implements HoldRepository {

    private final SpringJpaHoldRepository jpaRepository;

    public JpaHoldRepository(SpringJpaHoldRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Hold save(Hold hold) {
        return jpaRepository.save(hold);
    }

    @Override
    public Optional<Hold> findById(HoldId id) {
        return jpaRepository.findById_Value(id.value());
    }

    @Override
    public List<Hold> findByUserId(String userId) {
        return jpaRepository.findByUserId(userId);
    }

    @Override
    public List<Hold> findByWorkIdAndStatusInOrderByQueuePositionAsc(String workId, List<HoldStatus> statuses) {
        return jpaRepository.findByWorkIdAndStatusInOrderByQueuePositionAsc(workId, statuses);
    }

    @Override
    public boolean existsByWorkIdAndUserIdAndStatusIn(String workId, String userId, List<HoldStatus> statuses) {
        return jpaRepository.existsByWorkIdAndUserIdAndStatusIn(workId, userId, statuses);
    }

    @Override
    public long countByWorkIdAndStatusIn(String workId, List<HoldStatus> statuses) {
        return jpaRepository.countByWorkIdAndStatusIn(workId, statuses);
    }
}