package com.example.printemps.catalog.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaWorkRepository extends JpaRepository<JpaWorkRepository, Long> {
    List<JpaWorkRepository> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}
