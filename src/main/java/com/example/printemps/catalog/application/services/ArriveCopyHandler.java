package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.usecases.ArriveCopy;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.shared.error.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArriveCopyHandler implements ArriveCopy {

    private final CopyRepository copyRepository;
    private final HoldRepository holdRepository;

    public ArriveCopyHandler(CopyRepository copyRepository, HoldRepository holdRepository) {
        this.copyRepository = copyRepository;
        this.holdRepository = holdRepository;
    }

    @Override
    @Transactional
    public Copy execute(CopyId copyId) {
        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + copyId.value()));

        if (copy.getStatus() != CopyStatus.IN_TRANSIT) {
            throw new BusinessException("Copy is not in transit: " + copyId.value());
        }

        Optional<Hold> nextHold = holdRepository.findFirstByWorkIdAndStatusInOrderByQueuePositionAsc(
                copy.getWork().getId().value(),
                List.of(HoldStatus.REQUESTED)
        );

        if (nextHold.isPresent()) {
            Hold hold = nextHold.get();
            hold.markReadyForPickup(LocalDateTime.now().plusDays(3));
            holdRepository.save(hold);
            copy.updateStatus(CopyStatus.RESERVED);
        } else {
            copy.updateStatus(CopyStatus.AVAILABLE);
        }

        return copyRepository.save(copy);
    }
}
