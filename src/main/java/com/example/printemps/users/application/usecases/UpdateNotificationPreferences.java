package com.example.printemps.users.application.usecases;

import com.example.printemps.users.application.models.UpdateNotificationPreferencesRequest;

public interface UpdateNotificationPreferences {
    void handle(String ssoId, UpdateNotificationPreferencesRequest request);
}
