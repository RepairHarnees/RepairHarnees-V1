package com.repairharnees.application.dto;

import com.repairharnees.domain.entities.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDto {

    private java.util.UUID id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 120)
    private String nombre;

    @Size(max = 30)
    private String telefono;

    @Email(message = "Email invalido")
    @Size(max = 120)
    private String email;

    private Cliente.CanalNotificacion canalNotificacion;
}
