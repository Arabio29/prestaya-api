package com.example.prestamos.dto;

import com.example.prestamos.utils.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoDto {
    private double monto;
    private String modalidad;
    private int cuotas;
    private double tasaInteres;
    private Date fechaInicio;
    private double cuotaPagar;
    private double totalPagar;
    private double interesGenerado;
    private Date fechaActual;
    private Estado estado;
    private List<Date> fechasCuotas;
    private Long clienteId;
}