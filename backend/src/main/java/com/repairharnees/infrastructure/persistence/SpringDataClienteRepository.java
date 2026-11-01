package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByTelefono(String telefono);
}
