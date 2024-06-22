package com.example.prestamos.services;

import com.example.prestamos.dto.ClienteDto;
import com.example.prestamos.dto.PrestamoDto;
import com.example.prestamos.exceptions.ApiException;
import com.example.prestamos.models.Cliente;
import com.example.prestamos.models.FechasPrestamos;
import com.example.prestamos.models.Prestamo;
import com.example.prestamos.repositories.ClienteRepository;
import com.example.prestamos.repositories.PrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public PrestamoService(PrestamoRepository prestamoRepository, ClienteRepository clienteRepository) {
        this.prestamoRepository = prestamoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Prestamo savePrestamo(PrestamoDto prestamoDto) {
        Prestamo prestamo = getPrestamoFromDTO(prestamoDto);
        if (prestamo == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El prestamo no puede ser nulo.");
        }
        try {
            log.info("Prestamo guardado: " + prestamo);
            return prestamoRepository.save(prestamo);
        } catch (Exception e) {
            log.error("Error al guardar el prestamo: " + e.getMessage());
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el prestamo");
        }
    }

    public List<Prestamo> getAllPrestamos() {
        return prestamoRepository.findAll();
    }

    public Prestamo getPrestamoFromDTO(PrestamoDto prestamoDto) {
        Prestamo prestamo = new Prestamo();

        prestamo.setMonto(prestamoDto.getMonto());
        prestamo.setModalidad(prestamoDto.getModalidad());
        prestamo.setCuotas(prestamoDto.getCuotas());
        prestamo.setTasaInteres(prestamoDto.getTasaInteres());
        prestamo.setFechaInicio(prestamoDto.getFechaInicio());
        prestamo.setCuotaPagar(prestamoDto.getCuotaPagar());
        prestamo.setTotalPagar(prestamoDto.getTotalPagar());
        prestamo.setInteresGenerado(prestamoDto.getInteresGenerado());
        prestamo.setFechaActual(prestamoDto.getFechaActual());

        Cliente cliente = clienteRepository.findById(prestamoDto.getClienteId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        prestamo.setCliente(cliente);

        // Asignación de fechas de cuotas
        List<FechasPrestamos> fechasPrestamosList = new ArrayList<>();
        for (Date fecha : prestamoDto.getFechasCuotas()) {
            FechasPrestamos fechasPrestamos = new FechasPrestamos();
            fechasPrestamos.setFecha(fecha);
            fechasPrestamos.setPrestamo(prestamo);
            fechasPrestamosList.add(fechasPrestamos);
        }
        prestamo.setFechasPrestamos(fechasPrestamosList);

        return prestamo;
    }
}
