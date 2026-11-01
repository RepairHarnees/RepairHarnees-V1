package com.repairharnees.infrastructure.persistence;

import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.repositories.IComprobanteLogRepository;
import com.repairharnees.domain.repositories.IClienteRepository;
import com.repairharnees.domain.repositories.IEquipoRepository;
import com.repairharnees.domain.repositories.IOrdenServicioRepository;
import com.repairharnees.domain.repositories.IUsuarioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UnitOfWorkImpl implements IUnitOfWork {

    private final IClienteRepository clientes;
    private final IEquipoRepository equipos;
    private final IOrdenServicioRepository ordenes;
    private final IUsuarioRepository usuarios;
    private final IComprobanteLogRepository comprobanteLogs;

    public UnitOfWorkImpl(IClienteRepository clientes, IEquipoRepository equipos,
                          IOrdenServicioRepository ordenes, IUsuarioRepository usuarios,
                          IComprobanteLogRepository comprobanteLogs) {
        this.clientes = clientes;
        this.equipos = equipos;
        this.ordenes = ordenes;
        this.usuarios = usuarios;
        this.comprobanteLogs = comprobanteLogs;
    }

    @Override
    public IClienteRepository clientes() { return clientes; }

    @Override
    public IEquipoRepository equipos() { return equipos; }

    @Override
    public IOrdenServicioRepository ordenes() { return ordenes; }

    @Override
    public IUsuarioRepository usuarios() { return usuarios; }

    @Override
    public IComprobanteLogRepository comprobanteLogs() { return comprobanteLogs; }

    @Override
    @Transactional
    public void commit() {
    }
}
