package com.example.printemps.hold.application.gateways;

import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldId;
import com.example.printemps.hold.domain.HoldStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HoldRepository {
    Hold save(Hold hold);
    Optional<Hold> findById(HoldId id);
    List<Hold> findByUserId(String userId);

    long countByWorkIdAndStatusIn(String workId, List<HoldStatus> statuses);

    boolean existsByWorkIdAndUserIdAndStatusIn(String workId, String userId, List<HoldStatus> statuses);

    boolean existsByWorkIdAndStatusIn(String workId, List<HoldStatus> statuses);

    Optional<Hold> findByWorkIdAndUserIdAndStatus(String workId, String userId, HoldStatus status);

    Optional<Hold> findFirstByWorkIdAndStatusInOrderByQueuePositionAsc(String workId, List<HoldStatus> statuses);

    List<Hold> findByStatusAndPickupUntilBefore(HoldStatus status, LocalDateTime dateTime);

    boolean existsByCopyIdAndUserIdAndStatusIn(String copyId, String userId, List<HoldStatus> statuses);

    Optional<Hold> findFirstByCopyIdAndStatusInOrderByQueuePositionAsc(String copyId, List<HoldStatus> statuses);

    Optional<Hold> findByCopyIdAndUserIdAndStatus(String copyId, String userId, HoldStatus status);
}