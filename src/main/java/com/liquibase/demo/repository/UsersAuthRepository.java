package com.liquibase.demo.repository;

import com.liquibase.demo.model.UsersAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersAuthRepository extends JpaRepository<UsersAuth, Long> {
    Optional<UsersAuth> findByUsernameOrEmail(String username, String email);
}
