package com.example.prestamos.repositories;

import com.example.prestamos.models.FechasPrestamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FechaCuotaRepository extends JpaRepository<FechasPrestamos, Long> {

}
