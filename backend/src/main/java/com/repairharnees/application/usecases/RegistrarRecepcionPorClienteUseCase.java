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

import java.util.UUID;

@Service
public class RegistrarRecepcionPorClienteUseCase {

    private final IUnitOfWork unitOfWork;
    private final RecepcionValidator validator;

    public RegistrarRecepcionPorClienteUseCase(IUnitOfWork unitOfWork, RecepcionValidator validator) {
        this.unitOfWork = unitOfWork;
        this.validator = validator;
    }

    @Transactional
    public RecepcionResponse ejecutar(UUID clienteId, EquipoDto equipoDto, String observaciones) {
        Cliente cliente = unitOfWork.clientes().findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + clienteId));

        RecepcionRequest request = new RecepcionRequest();
        ClienteDto cd = new ClienteDto();
        cd.setNombre(cliente.getNombre());
        cd.setTelefono(cliente.getTelefono());
        cd.setEmail(cliente.getEmail());
        cd.setCanalNotificacion(cliente.getCanalNotificacion());
        request.setCliente(cd);
        request.setEquipo(equipoDto);
        request.setObservaciones(observaciones);
        validator.validar(request);

        Equipo equipo = unitOfWork.equipos().save(Equipo.builder()
                .marca(equipoDto.getMarca())
                .modelo(equipoDto.getModelo())
                .numeroSerie(equipoDto.getNumeroSerie())
                .descripcion(equipoDto.getDescripcion())
                .build());

        CodigoInterno codigo;
        do {
            codigo = CodigoInterno.generate();
        } while (unitOfWork.ordenes().existsByCodigoInterno(codigo));

        OrdenServicio orden = unitOfWork.ordenes().save(OrdenServicio.builder()
                .codigoInterno(codigo)
                .clienteId(cliente.getId())
                .equipoId(equipo.getId())
                .estado(OrdenServicio.EstadoOrden.RECIBIDO)
                .fechaRecepcion(java.time.LocalDateTime.now())
                .observaciones(observaciones)
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
                .mensaje("Recepcion registrada correctamente para cliente existente")
                .build();
    }
}
