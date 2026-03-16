package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.application.usecases.SearchUsers;
import com.example.printemps.users.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SearchUsersHandler implements SearchUsers {

    private final UserRepository userRepository;

    SearchUsersHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle() {
        return userRepository.findAll();
    }
}
