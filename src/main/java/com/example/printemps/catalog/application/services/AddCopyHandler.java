package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.models.AddCopyRequest;
import com.example.printemps.catalog.application.usecases.AddCopy;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import com.example.printemps.shared.DomainIdGenerator;
import org.springframework.stereotype.Service;

@Service
public class AddCopyHandler implements AddCopy {

    private final WorkRepository workRepository;
    private final CopyRepository copyRepository;
    private final DomainIdGenerator idGenerator;

    public AddCopyHandler(
            WorkRepository workRepository,
            CopyRepository copyRepository,
            DomainIdGenerator idGenerator
    ) {
        this.workRepository = workRepository;
        this.copyRepository = copyRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Copy execute(WorkId workId, AddCopyRequest request) {

        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        CopyId copyId = new CopyId(idGenerator.generate());

        Copy copy = Copy.create(copyId, work, request);

        return copyRepository.save(copy);
    }
}