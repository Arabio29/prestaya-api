package com.example.prestamos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prestamo")
@ToString(exclude = "cliente")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double monto;
    private String modalidad;
    private int cuotas;
    private double tasaInteres;
    private Date fechaInicio;
    private double cuotaPagar;
    private double totalPagar;
    private double interesGenerado;
    private Date fechaActual;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FechasPrestamos> fechasPrestamos;
}
