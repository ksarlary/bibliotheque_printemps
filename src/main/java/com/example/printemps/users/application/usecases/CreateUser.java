package com.example.printemps.users.application.usecases;

import com.example.printemps.users.application.models.CreateUserRequest;

public interface CreateUser {
    String handle(CreateUserRequest request);
}
