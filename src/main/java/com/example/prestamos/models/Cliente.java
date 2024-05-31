package com.example.prestamos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cedula;
    private String nombre;
    private Long celular;
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<Prestamo> prestamos;
}
