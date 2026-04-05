package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class JpaCopyRepository implements CopyRepository {

    private final SpringJpaCopyRepository repository;

    public JpaCopyRepository(SpringJpaCopyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Copy save(Copy copy) {
      return  repository.save(copy);
    }

    @Override
    public Optional<Copy> findById(CopyId id) {
        return repository.findById(id);

    }

    @Override
    public List<Copy> findByWorkId(WorkId workId) {
        return repository.findByWork_Id(workId);

    }
}