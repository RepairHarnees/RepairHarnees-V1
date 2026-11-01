package com.repairharnees.application.usecases;

import com.repairharnees.application.dto.RecepcionRequest;
import com.repairharnees.application.dto.RecepcionResponse;
import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.entities.OrdenServicio;
import com.repairharnees.domain.valueobjects.CodigoInterno;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActualizarOrdenUseCase {

    private final IUnitOfWork unitOfWork;

    public ActualizarOrdenUseCase(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional
    public RecepcionResponse ejecutar(String codigoInterno, RecepcionRequest request) {
        OrdenServicio orden = unitOfWork.ordenes().findByCodigoInterno(new CodigoInterno(codigoInterno))
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + codigoInterno));

        if (request.getObservaciones() != null) {
            orden.setObservaciones(request.getObservaciones());
        }
        if (request.getCliente() != null && request.getCliente().getNombre() != null
                && !request.getCliente().getNombre().isBlank()) {
            unitOfWork.clientes().findById(orden.getClienteId()).ifPresent(c -> {
                c.setNombre(request.getCliente().getNombre());
                c.setTelefono(request.getCliente().getTelefono());
                c.setEmail(request.getCliente().getEmail());
                unitOfWork.clientes().save(c);
            });
        }
        unitOfWork.ordenes().save(orden);
        unitOfWork.commit();

        return RecepcionResponse.builder()
                .ordenId(orden.getId())
                .codigoInterno(orden.getCodigoInterno().toString())
                .estado(orden.getEstado().name())
                .fechaRecepcion(orden.getFechaRecepcion())
                .mensaje("Orden actualizada correctamente")
                .build();
    }
}
