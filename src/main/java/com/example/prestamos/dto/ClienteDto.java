package com.example.prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private String nombre;
    private Long documento;
    private Long celular;
    private String direccion;
}
