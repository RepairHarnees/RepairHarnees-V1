package com.repairharnees.application.dto;

import com.repairharnees.domain.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {

    private UUID id;

    @NotBlank
    @Size(max = 60)
    private String username;

    @Size(min = 6, max = 100)
    private String password; // obligatoria al crear; al actualizar, null = no cambiarla

    @NotBlank
    @Size(max = 120)
    private String nombre;

    @NotNull
    private Usuario.Rol rol;

    private boolean activo = true;
}
