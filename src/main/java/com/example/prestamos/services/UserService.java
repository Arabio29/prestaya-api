package com.example.prestamos.services;

import com.example.prestamos.dto.ClienteDto;
import com.example.prestamos.dto.UsuarioDto;
import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Cliente;
import com.example.prestamos.models.Rol;
import com.example.prestamos.models.Usuario;
import com.example.prestamos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    RolService rolService;
    UsuarioRepository usuarioRepository;

    @Autowired
    public UserService(RolService rolService, UsuarioRepository usuarioRepository){
        this.rolService = rolService;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario saveUser(UsuarioDto usuarioDto){
        Usuario usuario = getUsuarioFromDTO(usuarioDto);

        if (usuario == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "El usuario no puede ser nulo.");
        }
        if (usuario.getRol().getId() != 1 && usuario.getRol().getId() != 2){
            throw new ApiException(HttpStatus.BAD_REQUEST, "El id del usuario debe ser 1 0 2");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioFromDTO(UsuarioDto usuarioDTO) {
        Rol rol = rolService.getRoleById(usuarioDTO.getRoleId());
        Usuario usuario = new Usuario();

        usuario.setUser(usuarioDTO.getUser());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setRol(rol);

        return usuario;
    }
}
