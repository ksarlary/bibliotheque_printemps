package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaWorkRepository implements WorkRepository {

    private final SpringJpaWorkRepository repository;

    public JpaWorkRepository(SpringJpaWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Work save(Work work) {
        WorkEntity entity = new WorkEntity(
                work.getId(),
                work.getIsbn(),
                work.getTitle(),
                work.getAuthors(),
                work.getPublisher(),
                work.getPublicationYear(),
                work.getCategory(),
                work.getType(),
                work.getLanguage(),
                work.getSubjects(),
                work.getDescription()
        );

        WorkEntity saved = repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<Work> findById(WorkId id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Work> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Work> search(String keyword) {
        return repository
                .findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Work toDomain(WorkEntity entity) {
        return new Work(
                entity.getId(),
                entity.getIsbn(),
                entity.getTitle(),
                entity.getAuthors(),
                entity.getPublisher(),
                entity.getPublicationYear(),
                entity.getCategory(),
                entity.getType(),
                entity.getLanguage(),
                entity.getSubjects(),
                entity.getDescription()
        );
    }
}