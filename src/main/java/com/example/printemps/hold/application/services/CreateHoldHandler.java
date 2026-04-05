package com.example.printemps.hold.application.services;

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

@Service
public class CreateHoldHandler implements CreateHold {

    private final HoldRepository holdRepository;
    private final DomainIdGenerator idGenerator;

    public CreateHoldHandler(HoldRepository holdRepository, DomainIdGenerator idGenerator) {
        this.holdRepository = holdRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public HoldId handle(CreateHoldRequest request) {
        List<HoldStatus> activeStatuses = List.of(
                HoldStatus.REQUESTED,
                HoldStatus.READY_FOR_PICKUP
        );

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

        HoldId holdId = new HoldId(idGenerator.generate());
        Hold hold = Hold.create(holdId, request.workId(), request.userId(), queuePosition);

        holdRepository.save(hold);
        return holdId;
    }
}
