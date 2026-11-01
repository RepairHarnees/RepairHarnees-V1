package com.repairharnees.domain.entities;

import com.repairharnees.domain.valueobjects.CodigoInterno;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ordenes_servicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "codigo_interno", nullable = false, unique = true, length = 20))
    private CodigoInterno codigoInterno;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "equipo_id", nullable = false)
    private UUID equipoId;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    @Column(name = "fecha_recepcion", nullable = false)
    private LocalDateTime fechaRecepcion;

    @Column(length = 500)
    private String observaciones;

    public enum EstadoOrden {
        RECIBIDO, EN_DIAGNOSTICO, COTIZADO, APROBADO, EN_REPARACION, LISTO, ENTREGADO, CANCELADO
    }
}
