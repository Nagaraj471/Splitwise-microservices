package com.splitwise.authservice.repositories;

import com.splitwise.authservice.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepo extends JpaRepository<AuthUser,Long> {

    Optional<AuthUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
