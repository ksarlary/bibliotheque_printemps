package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.usecases.SearchSimilarWorks;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchSimilarWorksHandler implements SearchSimilarWorks {

    private final WorkRepository workRepository;

    public SearchSimilarWorksHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public List<Work> execute(WorkId workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId.value()));
        return workRepository.findSimilarWorks(workId, work.getSubjects(), work.getAuthors());
    }
}
