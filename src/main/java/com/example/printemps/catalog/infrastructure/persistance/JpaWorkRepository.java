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
       return repository.save(work);
    }

    @Override
    public Optional<Work> findById(WorkId id) {
        return repository.findById(id);
    }

    @Override
    public List<Work> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Work> search(String keyword) {
        return repository
                .findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(keyword, keyword)
               ;
    }

}