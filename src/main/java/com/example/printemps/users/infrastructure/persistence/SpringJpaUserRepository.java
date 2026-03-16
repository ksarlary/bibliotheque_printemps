package com.example.printemps.users.infrastructure.persistence;

import com.example.printemps.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringJpaUserRepository extends JpaRepository<User, String> {
}
