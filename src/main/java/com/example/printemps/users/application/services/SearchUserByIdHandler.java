package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.application.usecases.SearchUserById;
import com.example.printemps.users.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class SearchUserByIdHandler implements SearchUserById {

    private final UserRepository userRepository;

    SearchUserByIdHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(String ssoId) {
        return userRepository.findById(ssoId);
    }
}
