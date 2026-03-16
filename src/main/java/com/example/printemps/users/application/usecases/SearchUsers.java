package com.example.printemps.users.application.usecases;

import com.example.printemps.users.domain.User;

import java.util.List;

public interface SearchUsers {
    List<User> handle();
}
