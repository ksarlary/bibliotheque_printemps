package com.example.printemps.penalties.infrastructure.persistence;

import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.penalties.domain.PenaltyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringJpaPenaltyRepository extends JpaRepository<Penalty, Long> {
    Optional<Penalty> findById(PenaltyId id);
    List<Penalty> findByUserId(String userId);
    List<Penalty> findByUserIdAndStatus(String userId, PenaltyStatus status);

}
