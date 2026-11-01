package com.repairharnees.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionResponse {

    private UUID ordenId;
    private String codigoInterno;
    private String clienteNombre;
    private String equipoMarca;
    private String equipoModelo;
    private String estado;
    private LocalDateTime fechaRecepcion;
    private String observaciones;
    private String mensaje;
}
