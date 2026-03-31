package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringJpaCopyRepository extends JpaRepository<CopyEntity, Long> {
    Optional<CopyEntity> findById(CopyId id);
    List<CopyEntity> findByWork_Id(WorkId workId);
    Optional<CopyEntity> findByBarcode(String barcode);
}