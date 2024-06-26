package com.example.prestamos.repositories;

import java.util.Optional;

import com.example.prestamos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
