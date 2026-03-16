package com.example.printemps.users.application.usecases;

import com.example.printemps.users.application.models.UpdatePolicyRequest;
import com.example.printemps.users.domain.Category;

public interface UpdatePolicy {
    void handle(Category category, UpdatePolicyRequest request);
}
