package com.example.prestamos.repositories;

import com.example.prestamos.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCedula(Long cedula);
    void deleteByCedula(Long cedula);
}
