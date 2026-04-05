package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.application.models.UpdateUserStatusRequest;
import com.example.printemps.users.application.usecases.UpdateUserStatus;
import com.example.printemps.users.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
class UpdateUserStatusHandler implements UpdateUserStatus {

    private final UserRepository userRepository;

    UpdateUserStatusHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void handle(String ssoId, UpdateUserStatusRequest request) {
        User user = userRepository.findById(ssoId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.updateStatus(request.status());
    }
}
