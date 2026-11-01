package com.repairharnees.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionRequest {

    @NotNull(message = "Los datos del cliente son obligatorios")
    @Valid
    private ClienteDto cliente;

    @NotNull(message = "Los datos del equipo son obligatorios")
    @Valid
    private EquipoDto equipo;

    private String observaciones;
}
