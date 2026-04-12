package com.example.printemps.users.application.models;

public record UpdateNotificationPreferencesRequest(
        boolean emailNotificationsEnabled,
        boolean reminderNotificationsEnabled
) {
}
