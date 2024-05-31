package com.example.prestamos.controllers;

import com.example.prestamos.dto.ClienteDto;
import com.example.prestamos.dto.UsuarioDto;
import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Cliente;
import com.example.prestamos.models.Usuario;
import com.example.prestamos.models.responses.CustomResponse;
import com.example.prestamos.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Controlador de Usuarios", description = "Controlador para gestionar las operaciones de los usuarios")
@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    UserService userService;

    @Autowired
    public UsuarioController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public CustomResponse<Usuario> saveCliente(@RequestBody UsuarioDto usuarioDto){
        try {
            return CustomResponse.success(userService.saveUser(usuarioDto));
        } catch (ApiException e) {
            return CustomResponse.error(e.getStatusCode(), e.getMessage());
        }
    }

}
