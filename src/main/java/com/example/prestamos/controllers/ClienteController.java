package com.example.prestamos.controllers;

import com.example.prestamos.dto.ClienteDto;
import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Cliente;
import com.example.prestamos.models.responses.CustomResponse;
import com.example.prestamos.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Controlador de Cliente", description = "Controlador para gestionar las operaciones de los clientes")
@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Crear un cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado."),
            @ApiResponse(responseCode = "400", description = "Error al crear al cliente.", content = @Content)
    })
    public CustomResponse<Cliente> saveCliente(@RequestBody ClienteDto clienteDto){
        try {
            return CustomResponse.success(clienteService.createClient(clienteDto));
        } catch (ApiException e) {
            return CustomResponse.error(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Mostrar clientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se mostraron los clientes."),
            @ApiResponse(responseCode = "400", description = "Error al mostrar los clientes.", content = @Content)
    })
    public CustomResponse <List<Cliente>> getAllClientes(){
        return CustomResponse.success(clienteService.getAllClients());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se mostraron los clientes."),
            @ApiResponse(responseCode = "400", description = "Error al mostrar los clientes.", content = @Content)
    })
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        try{
            clienteService.deleteClientById(id);
            return ResponseEntity.noContent().build();
        }catch(ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado."),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado.", content = @Content),
    })
    public CustomResponse<Cliente> getClientById(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClientById(id);
            return CustomResponse.success(cliente);
        } catch (ApiException e) {
            return CustomResponse.error(e.getStatusCode(), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado."),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado.", content = @Content),
    })
    public CustomResponse<Cliente> updateClientById(@PathVariable Long id, @RequestBody Cliente cliente) {
        try{
            return CustomResponse.success(clienteService.updateClient(id, cliente));
        }catch(ApiException e){
            return CustomResponse.error(e.getStatusCode(), e.getMessage());
        }
    }


   /* spring.application.name=prestamos
    spring.datasource.url=jdbc:mysql://${DB_SERVER}:${DB_PORT}/${DB_DATABASE}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
    spring.jpa.hibernate.ddl-auto=create
    springdoc.swagger-ui.path=/documentation.html
    */
}
