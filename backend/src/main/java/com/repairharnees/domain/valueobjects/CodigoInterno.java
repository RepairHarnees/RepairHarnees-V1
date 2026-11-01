package com.repairharnees.domain.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.UUID;

/**
 * Value Object: codigo interno unico de una orden de servicio.
 * Garantiza que cada orden tenga un identificador unico e inmutable.
 */
@Embeddable
public record CodigoInterno(String value) {

    public CodigoInterno {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El codigo interno no puede estar vacio");
        }
    }

    public static CodigoInterno generate() {
        String code = "RH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new CodigoInterno(code);
    }

    @Override
    public String toString() {
        return value;
    }
}
