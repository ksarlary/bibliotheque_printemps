package com.example.printemps.hold.infrastructure.persistence;

import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringJpaHoldRepository extends JpaRepository<Hold, Long> {

    List<Hold> findByUserId(String userId);

    Optional<Hold> findById_Value(String value);

    List<Hold> findByWorkIdAndStatusInOrderByQueuePositionAsc(String workId, List<HoldStatus> statuses);

    boolean existsByWorkIdAndUserIdAndStatusIn(String workId, String userId, List<HoldStatus> statuses);

    long countByWorkIdAndStatusIn(String workId, List<HoldStatus> statuses);
}
