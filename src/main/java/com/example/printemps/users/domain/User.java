package com.example.printemps.users.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "library_user")
@Access(AccessType.FIELD)
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String ssoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "email_notifications_enabled", nullable = false)
    private boolean emailNotificationsEnabled;

    @Column(name = "reminder_notifications_enabled", nullable = false)
    private boolean reminderNotificationsEnabled;

    protected User() {
    }

    private User(String ssoId, Category category, Status status) {
        this.ssoId = ssoId;
        this.category = category;
        this.status = status;
        this.emailNotificationsEnabled = true;
        this.reminderNotificationsEnabled = true;
    }

    public static User create(String ssoId, Category category) {
        return new User(ssoId, category, Status.ACTIVE);
    }

    public String getSsoId() {
        return ssoId;
    }

    public Category getCategory() {
        return category;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    public boolean isReminderNotificationsEnabled() {
        return reminderNotificationsEnabled;
    }

    public void updateNotificationPreferences(boolean emailNotificationsEnabled, boolean reminderNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
        this.reminderNotificationsEnabled = reminderNotificationsEnabled;
    }
}