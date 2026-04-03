package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.models.SearchWorksQuery;
import com.example.printemps.catalog.application.usecases.SearchWorks;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.catalog.domain.Work;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchWorksHandler implements SearchWorks {

    private final WorkRepository workRepository;
    private final CopyRepository copyRepository;

    public SearchWorksHandler(WorkRepository workRepository, CopyRepository copyRepository) {
        this.workRepository = workRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    public List<Work> execute(SearchWorksQuery query) {
        boolean hasFilters = query.keyword() != null || query.type() != null
                || query.language() != null || query.year() != null || query.subject() != null;

        List<Work> works = hasFilters
                ? workRepository.search(query)
                : workRepository.findAll();

        if (Boolean.TRUE.equals(query.availableOnly())) {
            works = works.stream()
                    .filter(work -> copyRepository.findByWorkId(work.getId()).stream()
                            .anyMatch(copy -> copy.getStatus() == CopyStatus.AVAILABLE))
                    .toList();
        }

        return works;
    }
}