package com.repairharnees.application.validators;

import com.repairharnees.application.dto.RecepcionRequest;
import org.springframework.stereotype.Component;

/**
 * Validador de negocio para la recepcion de un equipo.
 */
@Component
public class RecepcionValidator {

    public void validar(RecepcionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de recepcion no puede ser nula");
        }
        if (request.getCliente() == null || request.getCliente().getNombre() == null
                || request.getCliente().getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        if (request.getEquipo() == null || request.getEquipo().getMarca() == null
                || request.getEquipo().getMarca().isBlank()
                || request.getEquipo().getModelo() == null
                || request.getEquipo().getModelo().isBlank()) {
            throw new IllegalArgumentException("La marca y el modelo del equipo son obligatorios");
        }
    }
}
