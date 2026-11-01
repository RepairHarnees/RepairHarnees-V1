package com.repairharnees.domain.repositories;

import com.repairharnees.domain.entities.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface IClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(UUID id);
    Optional<Cliente> findByTelefono(String telefono);
    java.util.List<Cliente> findAll();
    void deleteById(UUID id);
}
