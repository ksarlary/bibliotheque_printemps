package com.example.printemps.catalog.application.gateways;

import com.example.printemps.catalog.application.models.SearchWorksQuery;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;

import java.util.List;
import java.util.Optional;

public interface WorkRepository {
    Work save(Work work);
    Optional<Work> findById(WorkId id);
    List<Work> findAll();
    List<Work> search(SearchWorksQuery query);
    List<Work> findSimilarWorks(WorkId workId, List<String> subjects, List<String> authors);
}