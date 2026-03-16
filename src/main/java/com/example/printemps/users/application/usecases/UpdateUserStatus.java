package com.example.printemps.users.application.usecases;

import com.example.printemps.users.application.models.UpdateUserStatusRequest;

public interface UpdateUserStatus {
    void handle(String ssoId, UpdateUserStatusRequest request);
}
