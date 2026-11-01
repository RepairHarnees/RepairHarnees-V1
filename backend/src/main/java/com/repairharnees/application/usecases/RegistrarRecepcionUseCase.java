package com.repairharnees.application.usecases;

import com.repairharnees.application.dto.ClienteDto;
import com.repairharnees.application.dto.EquipoDto;
import com.repairharnees.application.dto.RecepcionRequest;
import com.repairharnees.application.dto.RecepcionResponse;
import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.application.validators.RecepcionValidator;
import com.repairharnees.domain.entities.Cliente;
import com.repairharnees.domain.entities.Equipo;
import com.repairharnees.domain.entities.OrdenServicio;
import com.repairharnees.domain.valueobjects.CodigoInterno;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarRecepcionUseCase {

    private final IUnitOfWork unitOfWork;
    private final RecepcionValidator validator;

    public RegistrarRecepcionUseCase(IUnitOfWork unitOfWork, RecepcionValidator validator) {
        this.unitOfWork = unitOfWork;
        this.validator = validator;
    }

    @Transactional
    public RecepcionResponse ejecutar(RecepcionRequest request) {
        validator.validar(request);

        // 1. Crear o buscar cliente
        Cliente cliente = unitOfWork.clientes().findByTelefono(request.getCliente().getTelefono())
                .orElseGet(() -> unitOfWork.clientes().save(Cliente.builder()
                        .nombre(request.getCliente().getNombre())
                        .telefono(request.getCliente().getTelefono())
                        .email(request.getCliente().getEmail())
                        .canalNotificacion(request.getCliente().getCanalNotificacion())
                        .build()));

        // 2. Crear equipo
        Equipo equipo = unitOfWork.equipos().save(Equipo.builder()
                .marca(request.getEquipo().getMarca())
                .modelo(request.getEquipo().getModelo())
                .numeroSerie(request.getEquipo().getNumeroSerie())
                .descripcion(request.getEquipo().getDescripcion())
                .build());

        // 3. Generar codigo interno unico
        CodigoInterno codigo;
        do {
            codigo = CodigoInterno.generate();
        } while (unitOfWork.ordenes().existsByCodigoInterno(codigo));

        // 4. Crear orden de servicio
        OrdenServicio orden = unitOfWork.ordenes().save(OrdenServicio.builder()
                .codigoInterno(codigo)
                .clienteId(cliente.getId())
                .equipoId(equipo.getId())
                .estado(OrdenServicio.EstadoOrden.RECIBIDO)
                .fechaRecepcion(java.time.LocalDateTime.now())
                .observaciones(request.getObservaciones())
                .build());

        unitOfWork.commit();

        return RecepcionResponse.builder()
                .ordenId(orden.getId())
                .codigoInterno(orden.getCodigoInterno().toString())
                .clienteNombre(cliente.getNombre())
                .equipoMarca(equipo.getMarca())
                .equipoModelo(equipo.getModelo())
                .estado(orden.getEstado().name())
                .fechaRecepcion(orden.getFechaRecepcion())
                .mensaje("Recepcion registrada correctamente")
                .build();
    }
}
