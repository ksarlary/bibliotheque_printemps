package com.example.printemps.users.infrastructure.persistence;

import com.example.printemps.users.domain.Category;
import com.example.printemps.users.domain.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringJpaPolicyRepository extends JpaRepository<Policy, Category> {
}
