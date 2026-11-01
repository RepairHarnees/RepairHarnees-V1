package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.ComprobanteLog;
import com.repairharnees.domain.repositories.IComprobanteLogRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ComprobanteLogRepositoryAdapter implements IComprobanteLogRepository {

    private final SpringDataComprobanteLogRepository springRepo;

    public ComprobanteLogRepositoryAdapter(SpringDataComprobanteLogRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public ComprobanteLog save(ComprobanteLog log) { return springRepo.save(log); }

    @Override
    public List<ComprobanteLog> findAll() { return springRepo.findAll(); }

    @Override
    public List<ComprobanteLog> findByOrdenId(UUID ordenId) { return springRepo.findByOrdenId(ordenId); }

    @Override
    public List<ComprobanteLog> findByUsuarioId(UUID usuarioId) { return springRepo.findByUsuarioId(usuarioId); }
}
