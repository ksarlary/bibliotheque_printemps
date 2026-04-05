package com.example.printemps.hold.application.services;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.application.usecases.CancelHold;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CancelHoldHandler implements CancelHold {

    private final HoldRepository holdRepository;

    public CancelHoldHandler(HoldRepository holdRepository) {
        this.holdRepository = holdRepository;
    }

    @Override
    @Transactional
    public void handle(String holdId) {
        Hold hold = holdRepository.findById(new HoldId(holdId))
                .orElseThrow(() -> new IllegalArgumentException("Hold not found: " + holdId));

        hold.cancel();
        holdRepository.save(hold);
    }
}