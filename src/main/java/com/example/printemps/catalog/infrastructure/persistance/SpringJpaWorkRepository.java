package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.WorkId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringJpaWorkRepository extends JpaRepository<WorkEntity, Long> {
    Optional<WorkEntity> findById(WorkId id);
    List<WorkEntity> findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(String title, String authors);
}