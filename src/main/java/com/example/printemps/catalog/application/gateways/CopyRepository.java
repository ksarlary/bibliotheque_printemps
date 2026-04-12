package com.example.printemps.catalog.application.gateways;

import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.WorkId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CopyRepository {
    Copy save(Copy copy);
    Optional<Copy> findById(CopyId id);
    List<Copy> findByWorkId(WorkId workId);
    List<Copy> findByAcquiredAtBetween(LocalDateTime from, LocalDateTime to);
}