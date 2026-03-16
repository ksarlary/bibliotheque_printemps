package com.example.printemps.users.application.gateways;

import com.example.printemps.users.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(String ssoId);
    List<User> findAll();
}