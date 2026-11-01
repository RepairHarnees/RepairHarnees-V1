package com.repairharnees.application.usecases;

import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.valueobjects.CodigoInterno;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EliminarOrdenUseCase {

    private final IUnitOfWork unitOfWork;

    public EliminarOrdenUseCase(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional
    public void ejecutar(String codigoInterno) {
        var orden = unitOfWork.ordenes().findByCodigoInterno(new CodigoInterno(codigoInterno))
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + codigoInterno));
        // Spring Data deleteById
        unitOfWork.ordenes().deleteById(orden.getId());
        unitOfWork.commit();
    }
}
