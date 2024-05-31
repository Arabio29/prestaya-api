package com.example.prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoDto {
    private double monto;
    private String modalidad;
    private int cuotas;
    private double tasaInteres;
    private double cuotaPagar;
    private double totalPagar;
    private Date fechaInicio;
    private double interesGenerado;
    private Long clienteId;
}