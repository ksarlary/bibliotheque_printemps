package com.example.printemps.hold.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.application.models.CreateHoldRequest;
import com.example.printemps.hold.application.usecases.CreateHold;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldId;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.shared.DomainIdGenerator;
import com.example.printemps.shared.error.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CreateHoldHandler implements CreateHold {

    private final HoldRepository holdRepository;
    private final CopyRepository copyRepository;
    private final DomainIdGenerator idGenerator;

    public CreateHoldHandler(HoldRepository holdRepository, CopyRepository copyRepository, DomainIdGenerator idGenerator) {
        this.holdRepository = holdRepository;
        this.copyRepository = copyRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public HoldId handle(CreateHoldRequest request) {
        List<HoldStatus> activeStatuses = List.of(
                HoldStatus.REQUESTED,
                HoldStatus.READY_FOR_PICKUP
        );

        HoldId holdId = new HoldId(idGenerator.generate());
        Hold hold;

        if (request.copyId() != null && !request.copyId().isBlank()) {
            hold = createCopySpecificHold(request, activeStatuses, holdId);
        } else {
            hold = createWorkHold(request, activeStatuses, holdId);
        }

        holdRepository.save(hold);
        return holdId;
    }

    private Hold createWorkHold(CreateHoldRequest request, List<HoldStatus> activeStatuses, HoldId holdId) {
        boolean alreadyExists = holdRepository.existsByWorkIdAndUserIdAndStatusIn(
                request.workId(),
                request.userId(),
                activeStatuses
        );
        if (alreadyExists) {
            throw new BusinessException("User already has an active hold for this work");
        }

        int queuePosition = (int) holdRepository.countByWorkIdAndStatusIn(
                request.workId(),
                List.of(HoldStatus.REQUESTED)
        ) + 1;

        return Hold.create(holdId, request.workId(), request.userId(), queuePosition);
    }

    private Hold createCopySpecificHold(CreateHoldRequest request, List<HoldStatus> activeStatuses, HoldId holdId) {
        Copy copy = copyRepository.findById(new CopyId(request.copyId()))
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + request.copyId()));

        if (!copy.getWork().getId().value().equals(request.workId())) {
            throw new BusinessException("Copy does not belong to the specified work");
        }

        boolean alreadyExists = holdRepository.existsByCopyIdAndUserIdAndStatusIn(
                request.copyId(),
                request.userId(),
                activeStatuses
        );
        if (alreadyExists) {
            throw new BusinessException("User already has an active hold for this copy");
        }

        return Hold.createForCopy(holdId, request.workId(), request.copyId(), request.userId(), 1);
    }
}
