package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.application.models.UpdateUserStatusRequest;
import com.example.printemps.users.application.usecases.UpdateUserStatus;
import com.example.printemps.users.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
class UpdateUserStatusHandler implements UpdateUserStatus {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserStatusHandler.class);

    private final UserRepository userRepository;

    UpdateUserStatusHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void handle(String ssoId, UpdateUserStatusRequest request) {
        User user = userRepository.findById(ssoId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        var oldStatus = user.getStatus();
        user.updateStatus(request.status());

        log.info("[AUDIT] USER_STATUS_CHANGED userId={} oldStatus={} newStatus={}",
                ssoId, oldStatus, request.status());
    }
}
