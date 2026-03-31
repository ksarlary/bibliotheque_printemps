package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.models.UpdateCopyStatusRequest;
import com.example.printemps.catalog.application.usecases.UpdateCopyStatus;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import org.springframework.stereotype.Service;

@Service
public class UpdateCopyStatusHandler implements UpdateCopyStatus {

    private final CopyRepository copyRepository;

    public UpdateCopyStatusHandler(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public Copy execute(CopyId copyId, UpdateCopyStatusRequest request) {
        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new RuntimeException("Copy not found"));

        copy.updateStatus(request.status());

        return copyRepository.save(copy);
    }
}