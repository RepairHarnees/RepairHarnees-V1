package com.repairharnees.infrastructure.persistence;

import com.repairharnees.domain.entities.ComprobanteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataComprobanteLogRepository extends JpaRepository<ComprobanteLog, UUID> {
    List<ComprobanteLog> findByOrdenId(UUID ordenId);
    List<ComprobanteLog> findByUsuarioId(UUID usuarioId);
}
