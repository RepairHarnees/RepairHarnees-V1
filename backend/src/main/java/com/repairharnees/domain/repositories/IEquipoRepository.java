package com.repairharnees.domain.repositories;

import com.repairharnees.domain.entities.Equipo;

import java.util.Optional;
import java.util.UUID;

public interface IEquipoRepository {
    Equipo save(Equipo equipo);
    Optional<Equipo> findById(UUID id);
}
