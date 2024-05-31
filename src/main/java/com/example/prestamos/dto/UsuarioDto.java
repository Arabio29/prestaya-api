package com.example.prestamos.dto;

import com.example.prestamos.utils.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String user;
    private String password;
    private Long roleId;
}
