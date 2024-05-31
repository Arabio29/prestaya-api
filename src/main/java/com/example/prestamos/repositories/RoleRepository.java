package com.example.prestamos.repositories;

import com.example.prestamos.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findById(Long id);
}
