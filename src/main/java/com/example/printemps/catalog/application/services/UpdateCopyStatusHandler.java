package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.models.UpdateCopyStatusRequest;
import com.example.printemps.catalog.application.usecases.UpdateCopyStatus;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UpdateCopyStatusHandler implements UpdateCopyStatus {

    private static final Logger log = LoggerFactory.getLogger(UpdateCopyStatusHandler.class);

    private final CopyRepository copyRepository;

    public UpdateCopyStatusHandler(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public Copy execute(CopyId copyId, UpdateCopyStatusRequest request) {
        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NoSuchElementException("Copy not found"));

        var oldStatus = copy.getStatus();
        copy.updateStatus(request.status());
        Copy saved = copyRepository.save(copy);

        log.info("[AUDIT] COPY_STATUS_CHANGED copyId={} workId={} oldStatus={} newStatus={}",
                copyId.value(), copy.getWork().getId().value(), oldStatus, request.status());

        return saved;
    }
}