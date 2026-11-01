package com.repairharnees.application.usecases;

import com.repairharnees.application.dto.RecepcionResponse;
import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.entities.Cliente;
import com.repairharnees.domain.entities.Equipo;
import com.repairharnees.domain.entities.OrdenServicio;
import com.repairharnees.domain.valueobjects.CodigoInterno;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListarOrdenesUseCase {

    private final IUnitOfWork unitOfWork;

    public ListarOrdenesUseCase(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional(readOnly = true)
    public List<RecepcionResponse> ejecutar() {
        return unitOfWork.ordenes().findAll().stream().map(orden -> {
            Cliente cliente = unitOfWork.clientes().findById(orden.getClienteId()).orElse(null);
            Equipo equipo = unitOfWork.equipos().findById(orden.getEquipoId()).orElse(null);
            return RecepcionResponse.builder()
                    .ordenId(orden.getId())
                    .codigoInterno(orden.getCodigoInterno().toString())
                    .clienteNombre(cliente != null ? cliente.getNombre() : "-")
                    .equipoMarca(equipo != null ? equipo.getMarca() : "-")
                    .equipoModelo(equipo != null ? equipo.getModelo() : "-")
                    .estado(orden.getEstado().name())
                    .fechaRecepcion(orden.getFechaRecepcion())
                    .mensaje("OK")
                    .build();
        }).toList();
    }
}
