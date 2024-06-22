package com.example.prestamos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fechas_prestamos")
@ToString(exclude = "prestamo")
public class FechasPrestamos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    @JsonBackReference
    private Prestamo prestamo;
}
