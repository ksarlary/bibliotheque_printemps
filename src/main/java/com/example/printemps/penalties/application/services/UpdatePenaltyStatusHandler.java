package com.example.printemps.penalties.application.services;

import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.application.models.UpdatePenaltyStatusRequest;
import com.example.printemps.penalties.application.usecases.UpdatePenaltyStatus;
import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.penalties.domain.PenaltyStatus;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.Status;
import com.example.printemps.users.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
class UpdatePenaltyStatusHandler implements UpdatePenaltyStatus {

    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;

    UpdatePenaltyStatusHandler(PenaltyRepository penaltyRepository, UserRepository userRepository) {
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void handle(PenaltyId id, UpdatePenaltyStatusRequest request) {
        Penalty penalty = penaltyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Penalty not found"));

        penalty.updateStatus(request.status());
        penaltyRepository.save(penalty);

        if (request.status() == PenaltyStatus.PAID
                || request.status() == PenaltyStatus.CANCELLED) {
            User user = userRepository.findById(penalty.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User not found: " + penalty.getUserId()));

            boolean hasStillActivePenalties =
                    !penaltyRepository.findActiveByUserId(user.getSsoId()).isEmpty();

            if (!hasStillActivePenalties && user.getStatus() == Status.BLOCKED) {
                user.updateStatus(Status.ACTIVE);
                userRepository.save(user);
            }
        }
    }
}