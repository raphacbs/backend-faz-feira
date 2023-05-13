package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public User findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}