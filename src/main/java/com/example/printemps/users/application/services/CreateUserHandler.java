package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.application.models.CreateUserRequest;
import com.example.printemps.users.application.usecases.CreateUser;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
class CreateUserHandler implements CreateUser {

    private final UserRepository userRepository;

    CreateUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String handle(CreateUserRequest request) {
        User user = User.create(request.ssoId(), request.category());
        userRepository.save(user);
        return user.getSsoId();
    }
}
