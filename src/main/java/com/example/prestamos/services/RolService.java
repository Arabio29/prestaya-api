package com.example.prestamos.services;

import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Rol;
import com.example.prestamos.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    RoleRepository roleRepository;

    @Autowired
    public RolService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Rol getRoleById(Long id) {
        try {
            return roleRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Role with this id does not exist"));
        } catch (ApiException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Role with this id does not exist");
        }
    }
}
