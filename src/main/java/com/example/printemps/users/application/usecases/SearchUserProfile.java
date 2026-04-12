package com.example.printemps.users.application.usecases;

import com.example.printemps.users.infrastructure.rest.dto.UserProfileDTO;

public interface SearchUserProfile {
    UserProfileDTO handle(String ssoId);
}
