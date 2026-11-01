package com.repairharnees.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComprobanteLogDto {
    private UUID id;
    private UUID ordenId;
    private String codigoInterno;
    private UUID usuarioId;
    private String usuarioUsername;
    private LocalDateTime fechaImpresion;
    private String motivo;
}
