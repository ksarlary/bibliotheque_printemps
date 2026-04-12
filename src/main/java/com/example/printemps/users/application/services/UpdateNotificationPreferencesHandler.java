package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.application.models.UpdateNotificationPreferencesRequest;
import com.example.printemps.users.application.usecases.UpdateNotificationPreferences;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UpdateNotificationPreferencesHandler implements UpdateNotificationPreferences {

    private final UserRepository userRepository;

    public UpdateNotificationPreferencesHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void handle(String ssoId, UpdateNotificationPreferencesRequest request) {
        User user = userRepository.findById(ssoId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + ssoId));

        user.updateNotificationPreferences(
                request.emailNotificationsEnabled(),
                request.reminderNotificationsEnabled()
        );

        userRepository.save(user);
    }
}
