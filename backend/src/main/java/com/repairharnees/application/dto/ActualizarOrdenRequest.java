package com.repairharnees.application.dto;

import com.repairharnees.domain.entities.OrdenServicio;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarOrdenRequest {

    // Ambos campos son opcionales: null = no cambiar
    @Size(max = 500)
    private String observaciones;

    private OrdenServicio.EstadoOrden estado;
}
