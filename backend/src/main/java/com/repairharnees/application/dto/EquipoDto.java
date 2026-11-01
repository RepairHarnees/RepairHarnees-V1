package com.repairharnees.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoDto {

    @NotBlank(message = "La marca del equipo es obligatoria")
    @Size(max = 80)
    private String marca;

    @NotBlank(message = "El modelo del equipo es obligatorio")
    @Size(max = 80)
    private String modelo;

    @Size(max = 80)
    private String numeroSerie;

    @Size(max = 255)
    private String descripcion;
}
