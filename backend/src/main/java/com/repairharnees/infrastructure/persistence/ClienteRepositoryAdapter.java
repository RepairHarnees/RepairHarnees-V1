package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.Cliente;
import com.repairharnees.domain.repositories.IClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ClienteRepositoryAdapter implements IClienteRepository {

    private final SpringDataClienteRepository springRepo;

    public ClienteRepositoryAdapter(SpringDataClienteRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return springRepo.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(UUID id) {
        return springRepo.findById(id);
    }

    @Override
    public Optional<Cliente> findByTelefono(String telefono) {
        return springRepo.findByTelefono(telefono);
    }

    @Override
    public java.util.List<Cliente> findAll() {
        return springRepo.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        springRepo.deleteById(id);
    }
}
