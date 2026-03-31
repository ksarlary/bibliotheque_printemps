package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.models.CreateWorkRequest;
import com.example.printemps.catalog.application.usecases.CreateWork;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import com.example.printemps.shared.DomainIdGenerator;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkHandler implements CreateWork {

    private final WorkRepository workRepository;
    private final DomainIdGenerator idGenerator;

    public CreateWorkHandler(
            WorkRepository workRepository,
            DomainIdGenerator idGenerator
    ) {
        this.workRepository = workRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Work execute(CreateWorkRequest request) {

        WorkId workId = new WorkId(idGenerator.generate());

        Work work = Work.create(workId, request);

        return workRepository.save(work);
    }
}