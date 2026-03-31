package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.usecases.SearchWorks;
import com.example.printemps.catalog.domain.Work;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchWorksHandler implements SearchWorks {

    private final WorkRepository workRepository;

    public SearchWorksHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public List<Work> execute(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return workRepository.findAll();
        }
        return workRepository.search(keyword);
    }
}