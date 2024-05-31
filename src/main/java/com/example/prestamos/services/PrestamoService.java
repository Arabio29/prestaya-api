package com.example.prestamos.services;

import com.example.prestamos.dto.ClienteDto;
import com.example.prestamos.dto.PrestamoDto;
import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Cliente;
import com.example.prestamos.models.Prestamo;
import com.example.prestamos.repositories.PrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrestamoService {
    private PrestamoRepository prestamoRepository;

    @Autowired
    public PrestamoService(){
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo createClient(PrestamoDto prestamoDto){
        Prestamo prestamo = getPrestamoFromDTO(prestamoDto);
        if (prestamo == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "El prestamo no puede ser nulo.");
        }
        //if (prestamoRepository.existsByCedula(clienteDto.getDocumento())){
        //    throw new ApiException(HttpStatus.BAD_REQUEST, "Ya hay un prestamo registrado con esta cédula.");
       // }
        try{
            log.info("Prestamo guardado: " + prestamo);
            return prestamoRepository.save(prestamo);
        }catch (ApiException e){
            log.error("Error al guardar el prestamo: " + e.getMessage());
            return null;
        }

    }

    public Prestamo getPrestamoFromDTO(PrestamoDto prestamoDto) {
        Prestamo prestamo = new Prestamo();

        prestamo.setMonto(prestamoDto.getMonto());
        prestamo.setModalidad(prestamoDto.getModalidad());
        prestamo.setCuotas(prestamoDto.getCuotas());
        prestamo.setTasaInteres(prestamoDto.getTasaInteres());
        prestamo.setCuotaPagar(prestamoDto.getCuotaPagar());
        prestamo.setTotalPagar(prestamoDto.getTotalPagar());
        prestamo.setFechaInicio(prestamoDto.getFechaInicio());
        prestamo.setInteresGenerado(prestamoDto.getInteresGenerado());



        return prestamo;
    }

    //add
}
