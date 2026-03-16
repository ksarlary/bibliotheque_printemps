package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.usecases.SearchPolicyByCategory;
import com.example.printemps.users.domain.Category;
import com.example.printemps.users.domain.Policy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class SearchPolicyByCategoryHandler implements SearchPolicyByCategory {

    private final PolicyRepository policyRepository;

    SearchPolicyByCategoryHandler(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public Optional<Policy> handle(Category category) {
        return policyRepository.findById(category);
    }
}
