package com.repairharnees.domain.repositories;

import com.repairharnees.domain.entities.OrdenServicio;
import com.repairharnees.domain.valueobjects.CodigoInterno;

import java.util.Optional;
import java.util.UUID;

public interface IOrdenServicioRepository {
    OrdenServicio save(OrdenServicio orden);
    Optional<OrdenServicio> findById(UUID id);
    Optional<OrdenServicio> findByCodigoInterno(CodigoInterno codigo);
    boolean existsByCodigoInterno(CodigoInterno codigo);
    java.util.List<OrdenServicio> findAll();
    void deleteById(UUID id);
}
