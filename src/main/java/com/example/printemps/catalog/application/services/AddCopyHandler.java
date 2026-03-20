package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.gateways.WorkRepository;
import com.example.printemps.catalog.application.models.AddCopyRequest;
import com.example.printemps.catalog.application.usecases.AddCopy;
import com.example.printemps.catalog.domain.Copy;
import org.springframework.stereotype.Service;

@Service
public class AddCopyHandler implements AddCopy {

    private final WorkRepository workRepository;
    private final CopyRepository copyRepository;

    public AddCopyHandler(WorkRepository workRepository, CopyRepository copyRepository) {
        this.workRepository = workRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    public Copy execute(AddCopyRequest request) {
        workRepository.findById(request.workId())
                .orElseThrow(() -> new RuntimeException("Oeuvre introuvable"));

        Copy copy = new Copy(
                request.workId(),
                request.barcode(),
                request.location()
        );

        return copyRepository.save(copy);
    }
}