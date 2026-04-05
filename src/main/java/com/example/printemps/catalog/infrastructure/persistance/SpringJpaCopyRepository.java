package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringJpaCopyRepository extends JpaRepository<Copy, Long> {
    Optional<Copy> findById(CopyId id);
    List<Copy> findByWork_Id(WorkId workId);
    Optional<Copy> findByBarcode(String barcode);
}