package com.example.prestamos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double monto;
    private String modalidad;
    private int cuotas;
    private double tasaInteres;
    private double cuotaPagar;
    private double totalPagar;
    private Date fechaInicio;
    private double interesGenerado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "prestamo")
    private List<Pago> pagos;
}
