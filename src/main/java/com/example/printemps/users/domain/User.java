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

    protected User() {
    }

    private User(String ssoId, Category category, Status status) {
        this.ssoId = ssoId;
        this.category = category;
        this.status = status;
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
}