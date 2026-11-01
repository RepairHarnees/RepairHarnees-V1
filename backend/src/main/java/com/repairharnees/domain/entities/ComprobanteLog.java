package com.repairharnees.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comprobante_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComprobanteLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "orden_id", nullable = false)
    private UUID ordenId;

    @Column(name = "codigo_interno", nullable = false, length = 20)
    private String codigoInterno;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "usuario_username", nullable = false, length = 60)
    private String usuarioUsername;

    @Column(name = "fecha_impresion", nullable = false)
    private LocalDateTime fechaImpresion;

    @Column(length = 255)
    private String motivo;
}
