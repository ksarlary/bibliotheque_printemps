package com.example.printemps.hold.infrastructure.persistence;

import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpringJpaHoldRepository extends JpaRepository<Hold, Long> {

    Optional<Hold> findById_Value(String value);

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

    long countByStatus(HoldStatus status);

    long countByStatusIn(List<HoldStatus> statuses);

    List<Hold> findByStatusIn(List<HoldStatus> statuses);
}
