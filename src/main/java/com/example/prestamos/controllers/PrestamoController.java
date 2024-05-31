package com.example.prestamos.controllers;

import com.example.prestamos.dto.PrestamoDto;
import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Prestamo;
import com.example.prestamos.models.responses.CustomResponse;
import com.example.prestamos.services.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/prestamo")
public class PrestamoController {

    private PrestamoService prestamoService;

    @Autowired
    public PrestamoController(PrestamoService prestamoService){
        this.prestamoService = prestamoService;
    }

    @PostMapping
    @Operation(summary = "Crear un prestamo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo creado."),
            @ApiResponse(responseCode = "400", description = "Error al crear el prestamo.", content = @Content)
    })
    public CustomResponse<Prestamo> savePrestamo(@RequestBody PrestamoDto prestamoDto){
        try{
            return CustomResponse.success(prestamoService.savePrestamo(prestamoDto));
        }catch (ApiException e){
            return CustomResponse.error(e.getStatusCode(), e.getMessage());
        }
    }



    //add
}
