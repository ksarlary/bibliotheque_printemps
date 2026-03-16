package com.example.printemps.users.application.usecases;

import com.example.printemps.users.domain.User;

import java.util.Optional;

public interface SearchUserById {
    Optional<User> handle(String ssoId);
}