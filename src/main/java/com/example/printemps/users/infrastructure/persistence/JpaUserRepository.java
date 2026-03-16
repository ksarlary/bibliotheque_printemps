package com.example.printemps.users.infrastructure.persistence;

import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JpaUserRepository implements UserRepository {

    private final SpringJpaUserRepository jpaRepository;

    JpaUserRepository(SpringJpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(User user) {
        jpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(String ssoId) {
        return jpaRepository.findById(ssoId);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll();
    }
}
