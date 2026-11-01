package com.repairharnees.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 30)
    private String telefono;

    @Column(length = 120)
    private String email;

    @Column(name = "canal_notificacion", length = 20)
    @Enumerated(EnumType.STRING)
    private CanalNotificacion canalNotificacion;

    public enum CanalNotificacion {
        EMAIL, WHATSAPP, SMS
    }
}
