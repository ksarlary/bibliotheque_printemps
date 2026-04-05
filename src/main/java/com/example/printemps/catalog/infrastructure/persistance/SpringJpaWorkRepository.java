package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringJpaWorkRepository extends JpaRepository<Work, Long> {
    Optional<Work> findById(WorkId id);
    List<Work> findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(String title, String authors);
}