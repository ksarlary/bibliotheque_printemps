package com.example.printemps.users.application.usecases;

import com.example.printemps.users.domain.Category;
import com.example.printemps.users.domain.Policy;

import java.util.Optional;

public interface SearchPolicyByCategory {
    Optional<Policy> handle(Category category);
}
