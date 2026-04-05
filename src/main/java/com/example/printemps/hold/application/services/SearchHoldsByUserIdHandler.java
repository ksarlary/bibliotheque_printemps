package com.example.printemps.hold.application.services;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.application.usecases.SearchHoldsByUserId;
import com.example.printemps.hold.domain.Hold;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHoldsByUserIdHandler implements SearchHoldsByUserId {

    private final HoldRepository holdRepository;

    public SearchHoldsByUserIdHandler(HoldRepository holdRepository) {
        this.holdRepository = holdRepository;
    }

    @Override
    public List<Hold> handle(String userId) {
        return holdRepository.findByUserId(userId);
    }
}
