package com.repairharnees.application.ports;

import com.repairharnees.domain.repositories.IClienteRepository;
import com.repairharnees.domain.repositories.IComprobanteLogRepository;
import com.repairharnees.domain.repositories.IEquipoRepository;
import com.repairharnees.domain.repositories.IOrdenServicioRepository;
import com.repairharnees.domain.repositories.IUsuarioRepository;

/**
 * Unit of Work: agrupa los repositorios y gestiona la transaccion.
 */
public interface IUnitOfWork {
    IClienteRepository clientes();
    IEquipoRepository equipos();
    IOrdenServicioRepository ordenes();
    IUsuarioRepository usuarios();
    IComprobanteLogRepository comprobanteLogs();
    void commit();
}
