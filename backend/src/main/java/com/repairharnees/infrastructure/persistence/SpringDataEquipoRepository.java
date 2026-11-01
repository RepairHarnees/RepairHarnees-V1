package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataEquipoRepository extends JpaRepository<Equipo, UUID> {
}
