package com.example.printemps.users.infrastructure.persistence;

import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.domain.Category;
import com.example.printemps.users.domain.Policy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JpaPolicyRepository implements PolicyRepository {

    private final SpringJpaPolicyRepository jpaRepository;

    JpaPolicyRepository(SpringJpaPolicyRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Policy policy) {
        jpaRepository.save(policy);
    }

    @Override
    public Optional<Policy> findById(Category category) {
        return jpaRepository.findById(category);
    }

    @Override
    public List<Policy> findAll() {
        return jpaRepository.findAll();
    }
}
