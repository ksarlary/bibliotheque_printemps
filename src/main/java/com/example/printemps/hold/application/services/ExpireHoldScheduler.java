package com.example.printemps.hold.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.catalog.domain.WorkId;
import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldStatus;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpireHoldScheduler {

    private final HoldRepository holdRepository;
    private final CopyRepository copyRepository;

    public ExpireHoldScheduler(HoldRepository holdRepository, CopyRepository copyRepository) {
        this.holdRepository = holdRepository;
        this.copyRepository = copyRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireReadyForPickupHolds() {
        List<Hold> expiredCandidates = holdRepository.findByStatusAndPickupUntilBefore(
                HoldStatus.READY_FOR_PICKUP,
                LocalDateTime.now()
        );

        for (Hold expiredHold : expiredCandidates) {
            processExpiredHold(expiredHold);
        }
    }

    private void processExpiredHold(Hold expiredHold) {
        String workIdValue = expiredHold.getWorkId();

        expiredHold.expire();
        holdRepository.save(expiredHold);

        Optional<Hold> nextHold = holdRepository.findFirstByWorkIdAndStatusInOrderByQueuePositionAsc(
                workIdValue,
                List.of(HoldStatus.REQUESTED)
        );

        if (nextHold.isPresent()) {
            Hold hold = nextHold.get();
            hold.markReadyForPickup(LocalDateTime.now().plusDays(3));
            holdRepository.save(hold);
            return;
        }

        WorkId workId = new WorkId(workIdValue);
        List<Copy> copies = copyRepository.findByWorkId(workId);

        copies.stream()
                .filter(copy -> copy.getStatus() == CopyStatus.RESERVED)
                .findFirst()
                .ifPresent(copy -> {
                    copy.updateStatus(CopyStatus.AVAILABLE);
                    copyRepository.save(copy);
                });
    }
}