package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.Equipo;
import com.repairharnees.domain.repositories.IEquipoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EquipoRepositoryAdapter implements IEquipoRepository {

    private final SpringDataEquipoRepository springRepo;

    public EquipoRepositoryAdapter(SpringDataEquipoRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Equipo save(Equipo equipo) {
        return springRepo.save(equipo);
    }

    @Override
    public Optional<Equipo> findById(UUID id) {
        return springRepo.findById(id);
    }
}
