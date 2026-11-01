package com.repairharnees.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionPorClienteRequest {
    @NotNull
    @Valid
    private EquipoDto equipo;
    private String observaciones;
}
