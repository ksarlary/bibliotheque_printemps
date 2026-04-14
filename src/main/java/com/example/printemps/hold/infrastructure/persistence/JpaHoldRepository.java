package com.example.printemps.hold.infrastructure.persistence;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldId;
import com.example.printemps.hold.domain.HoldStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public long countByWorkIdAndStatusIn(String workId, List<HoldStatus> statuses) {
        return jpaRepository.countByWorkIdAndStatusIn(workId, statuses);
    }

    @Override
    public boolean existsByWorkIdAndUserIdAndStatusIn(String workId, String userId, List<HoldStatus> statuses) {
        return jpaRepository.existsByWorkIdAndUserIdAndStatusIn(workId, userId, statuses);
    }

    @Override
    public boolean existsByWorkIdAndStatusIn(String workId, List<HoldStatus> statuses) {
        return jpaRepository.existsByWorkIdAndStatusIn(workId, statuses);
    }

    @Override
    public Optional<Hold> findByWorkIdAndUserIdAndStatus(String workId, String userId, HoldStatus status) {
        return jpaRepository.findByWorkIdAndUserIdAndStatus(workId, userId, status);
    }

    @Override
    public Optional<Hold> findFirstByWorkIdAndStatusInOrderByQueuePositionAsc(String workId, List<HoldStatus> statuses) {
        return jpaRepository.findFirstByWorkIdAndStatusInOrderByQueuePositionAsc(workId, statuses);
    }

    @Override
    public List<Hold> findByStatusAndPickupUntilBefore(HoldStatus status, LocalDateTime dateTime) {
        return jpaRepository.findByStatusAndPickupUntilBefore(status, dateTime);
    }

    @Override
    public boolean existsByCopyIdAndUserIdAndStatusIn(String copyId, String userId, List<HoldStatus> statuses) {
        return jpaRepository.existsByCopyIdAndUserIdAndStatusIn(copyId, userId, statuses);
    }

    @Override
    public Optional<Hold> findFirstByCopyIdAndStatusInOrderByQueuePositionAsc(String copyId, List<HoldStatus> statuses) {
        return jpaRepository.findFirstByCopyIdAndStatusInOrderByQueuePositionAsc(copyId, statuses);
    }

    @Override
    public Optional<Hold> findByCopyIdAndUserIdAndStatus(String copyId, String userId, HoldStatus status) {
        return jpaRepository.findByCopyIdAndUserIdAndStatus(copyId, userId, status);
    }
}