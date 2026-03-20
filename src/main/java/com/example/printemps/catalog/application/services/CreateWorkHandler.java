package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.models.CreateWorkRequest;
import com.example.printemps.catalog.application.usecases.CreateWork;
import com.example.printemps.catalog.domain.Work;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkHandler implements CreateWork {

    private final WorkRepository workRepository;

    public CreateWorkHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public Work execute(CreateWorkRequest request) {
        Work work = new Work(
                request.isbn(),
                request.title(),
                request.author(),
                request.publisher(),
                request.publicationYear(),
                request.category(),
                request.description()
        );

        return workRepository.save(work);
    }
}
