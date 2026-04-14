package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.models.TransferCopyRequest;
import com.example.printemps.catalog.application.usecases.TransferCopy;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.shared.error.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TransferCopyHandler implements TransferCopy {

    private final CopyRepository copyRepository;

    public TransferCopyHandler(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    @Transactional
    public Copy execute(CopyId copyId, TransferCopyRequest request) {
        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + copyId.value()));

        if (copy.getStatus() == CopyStatus.ON_LOAN) {
            throw new BusinessException("Cannot transfer a copy that is currently on loan");
        }
        if (copy.getStatus() == CopyStatus.IN_TRANSIT) {
            throw new BusinessException("Copy is already in transit");
        }
        if (copy.getLocation().equals(request.targetLocation())) {
            throw new BusinessException("Copy is already at location: " + request.targetLocation());
        }

        copy.updateLocation(request.targetLocation());
        copy.updateStatus(CopyStatus.IN_TRANSIT);

        return copyRepository.save(copy);
    }
}
