package com.example.printemps.hold.application.services;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.application.usecases.CancelHold;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldId;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CancelHoldHandler implements CancelHold {

    private static final Logger log = LoggerFactory.getLogger(CancelHoldHandler.class);

    private final HoldRepository holdRepository;

    public CancelHoldHandler(HoldRepository holdRepository) {
        this.holdRepository = holdRepository;
    }

    @Override
    @Transactional
    public void handle(String holdId) {
        Hold hold = holdRepository.findById(new HoldId(holdId))
                .orElseThrow(() -> new NoSuchElementException("Hold not found: " + holdId));

        hold.cancel();
        holdRepository.save(hold);

        log.info("[AUDIT] CANCEL_HOLD holdId={} workId={} userId={}",
                holdId, hold.getWorkId(), hold.getUserId());
    }
}