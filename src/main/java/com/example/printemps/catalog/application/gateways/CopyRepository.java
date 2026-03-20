package com.example.printemps.catalog.application.gateways;

import com.example.printemps.catalog.domain.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyRepository {
    Copy save(Copy copy);
    Optional<Copy> findById(Long id);
    List<Copy> findByWorkId(Long workId);
}