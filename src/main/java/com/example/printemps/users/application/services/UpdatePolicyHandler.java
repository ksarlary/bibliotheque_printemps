package com.example.printemps.users.application.services;

import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.models.UpdatePolicyRequest;
import com.example.printemps.users.application.usecases.UpdatePolicy;
import com.example.printemps.users.domain.Category;
import com.example.printemps.users.domain.Policy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UpdatePolicyHandler implements UpdatePolicy {

    private final PolicyRepository policyRepository;

    UpdatePolicyHandler(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    @Transactional
    public void handle(Category category, UpdatePolicyRequest request) {
        Policy policy = policyRepository.findById(category)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.update(
                request.maxLoans(),
                request.loanDurationDays(),
                request.maxRenewals()
        );
    }
}
