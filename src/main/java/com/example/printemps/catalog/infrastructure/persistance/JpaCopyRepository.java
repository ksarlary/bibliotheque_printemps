package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCopyRepository implements CopyRepository {

    private final SpringJpaCopyRepository repository;

    public JpaCopyRepository(SpringJpaCopyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Copy save(Copy copy) {
        WorkEntity workEntity = new WorkEntity(
                copy.getWork().getId(),
                copy.getWork().getIsbn(),
                copy.getWork().getTitle(),
                copy.getWork().getAuthors(),
                copy.getWork().getPublisher(),
                copy.getWork().getPublicationYear(),
                copy.getWork().getCategory(),
                copy.getWork().getType(),
                copy.getWork().getLanguage(),
                copy.getWork().getSubjects(),
                copy.getWork().getDescription()
        );

        CopyEntity entity = new CopyEntity(
                copy.getId(),
                workEntity,
                copy.getBarcode(),
                copy.getStatus(),
                copy.getLocation()
        );

        CopyEntity saved = repository.save(entity);

        return Copy.restore(
                saved.getId(),
                copy.getWork(),
                saved.getBarcode(),
                saved.getStatus(),
                saved.getLocation()
        );
    }

    @Override
    public Optional<Copy> findById(CopyId id) {
        return repository.findById(id)
                .map(entity -> Copy.restore(
                        entity.getId(),
                        new Work(
                                entity.getWork().getId(),
                                entity.getWork().getIsbn(),
                                entity.getWork().getTitle(),
                                entity.getWork().getAuthors(),
                                entity.getWork().getPublisher(),
                                entity.getWork().getPublicationYear(),
                                entity.getWork().getCategory(),
                                entity.getWork().getType(),
                                entity.getWork().getLanguage(),
                                entity.getWork().getSubjects(),
                                entity.getWork().getDescription()
                        ),
                        entity.getBarcode(),
                        entity.getStatus(),
                        entity.getLocation()
                ));
    }

    @Override
    public List<Copy> findByWorkId(WorkId workId) {
        return repository.findByWork_Id(workId).stream()
                .map(entity -> Copy.restore(
                        entity.getId(),
                        new Work(
                                entity.getWork().getId(),
                                entity.getWork().getIsbn(),
                                entity.getWork().getTitle(),
                                entity.getWork().getAuthors(),
                                entity.getWork().getPublisher(),
                                entity.getWork().getPublicationYear(),
                                entity.getWork().getCategory(),
                                entity.getWork().getType(),
                                entity.getWork().getLanguage(),
                                entity.getWork().getSubjects(),
                                entity.getWork().getDescription()
                        ),
                        entity.getBarcode(),
                        entity.getStatus(),
                        entity.getLocation()
                ))
                .toList();
    }
}