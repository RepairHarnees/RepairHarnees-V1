package com.repairharnees.domain.repositories;

import com.repairharnees.domain.entities.ComprobanteLog;

import java.util.List;
import java.util.UUID;

public interface IComprobanteLogRepository {
    ComprobanteLog save(ComprobanteLog log);
    List<ComprobanteLog> findAll();
    List<ComprobanteLog> findByOrdenId(UUID ordenId);
    List<ComprobanteLog> findByUsuarioId(UUID usuarioId);
}
