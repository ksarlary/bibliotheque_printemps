package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.models.UpdateWorkRequest;
import com.example.printemps.catalog.application.usecases.UpdateWork;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UpdateWorkHandler implements UpdateWork {

    private final WorkRepository workRepository;

    public UpdateWorkHandler(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public Work execute(WorkId workId, UpdateWorkRequest request) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new NoSuchElementException("Work not found"));

        work.update(
                request.title(),
                request.authors(),
                request.publisher(),
                request.publicationYear(),
                request.category(),
                request.type(),
                request.language(),
                request.subjects(),
                request.description()
        );

        return workRepository.save(work);
    }
}