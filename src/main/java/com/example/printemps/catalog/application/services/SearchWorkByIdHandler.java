package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.usecases.SearchWorkById;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.stereotype.Service;

@Service
public class SearchWorkByIdHandler implements SearchWorkById {

    private final WorkRepository workRepository;

    public SearchWorkByIdHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public Work execute(WorkId workId) {
        return workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));
    }
}