package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.OrdenServicio;
import com.repairharnees.domain.valueobjects.CodigoInterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataOrdenRepository extends JpaRepository<OrdenServicio, UUID> {
    Optional<OrdenServicio> findByCodigoInterno_Value(String value);
    boolean existsByCodigoInterno_Value(String value);
}
