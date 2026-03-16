package com.example.printemps.users.application.gateways;

import com.example.printemps.users.domain.Category;
import com.example.printemps.users.domain.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository {
    void save(Policy policy);
    Optional<Policy> findById(Category category);
    List<Policy> findAll();
}
