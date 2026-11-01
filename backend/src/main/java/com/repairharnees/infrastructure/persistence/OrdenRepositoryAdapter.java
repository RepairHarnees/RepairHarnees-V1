package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.OrdenServicio;
import com.repairharnees.domain.repositories.IOrdenServicioRepository;
import com.repairharnees.domain.valueobjects.CodigoInterno;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrdenRepositoryAdapter implements IOrdenServicioRepository {

    private final SpringDataOrdenRepository springRepo;

    public OrdenRepositoryAdapter(SpringDataOrdenRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public OrdenServicio save(OrdenServicio orden) {
        return springRepo.save(orden);
    }

    @Override
    public Optional<OrdenServicio> findById(UUID id) {
        return springRepo.findById(id);
    }

    @Override
    public Optional<OrdenServicio> findByCodigoInterno(CodigoInterno codigo) {
        return springRepo.findByCodigoInterno_Value(codigo.value());
    }

    @Override
    public boolean existsByCodigoInterno(CodigoInterno codigo) {
        return springRepo.existsByCodigoInterno_Value(codigo.value());
    }

    @Override
    public java.util.List<OrdenServicio> findAll() {
        return springRepo.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        springRepo.deleteById(id);
    }
}
