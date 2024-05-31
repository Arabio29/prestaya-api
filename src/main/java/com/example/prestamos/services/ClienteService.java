package com.example.prestamos.services;

import com.example.prestamos.dto.ClienteDto;
import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Cliente;
import com.example.prestamos.repositories.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public Cliente createClient(ClienteDto clienteDto){
        Cliente cliente = getClientFromDTO(clienteDto);
        if (cliente == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "El cliente no puede ser nulo.");
        }
        if (clienteRepository.existsByCedula(clienteDto.getDocumento())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Ya hay un cliente registrado con esta cédula.");
        }
        try{
            log.info("Cliente guardado: " + cliente);
            return clienteRepository.save(cliente);
        }catch (ApiException e){
            log.error("Error al guardar el cliente: " + e.getMessage());
            return null;
        }

    }

    public List<Cliente> getAllClients(){
        return clienteRepository.findAll();
    }

    public void deleteClientById(Long id){
        if (!clienteRepository.existsById(id)){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cliente no encontrado.");
        }
        log.info("cliente eliminado con exito.");
        clienteRepository.deleteById(id);
    }

    public Cliente getClientById(Long id){
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (optionalCliente.isPresent()){
            return optionalCliente.get();
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND, "El cliente no fué encontrado.");
        }
    }

    public Cliente updateClient(Long id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Proveedor no encontrado.");
        }
        cliente.setId(id);
        log.info("Cliente actualizado: " + cliente);
        return clienteRepository.save(cliente);
    }




    public Cliente getClientFromDTO(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setCedula(clienteDto.getDocumento());
        cliente.setCelular(clienteDto.getCelular());
        cliente.setDireccion(clienteDto.getDireccion());

        return cliente;
    }

}
