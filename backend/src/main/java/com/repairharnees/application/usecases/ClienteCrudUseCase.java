package com.repairharnees.application.usecases;

import com.repairharnees.application.dto.ClienteDto;
import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.entities.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteCrudUseCase {

    private final IUnitOfWork unitOfWork;

    public ClienteCrudUseCase(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional
    public ClienteDto crear(ClienteDto dto) {
        Cliente c = Cliente.builder()
                .nombre(dto.getNombre())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .canalNotificacion(dto.getCanalNotificacion())
                .build();
        c = unitOfWork.clientes().save(c);
        unitOfWork.commit();
        dto.setId(c.getId() != null ? c.getId() : null);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ClienteDto> listar() {
        return unitOfWork.clientes().findAll().stream().map(c -> ClienteDto.builder()
                .id(c.getId()).nombre(c.getNombre()).telefono(c.getTelefono()).email(c.getEmail())
                .canalNotificacion(c.getCanalNotificacion()).build()).toList();
    }

    @Transactional(readOnly = true)
    public ClienteDto obtener(UUID id) {
        Cliente c = unitOfWork.clientes().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        return ClienteDto.builder().id(c.getId()).nombre(c.getNombre()).telefono(c.getTelefono())
                .email(c.getEmail()).canalNotificacion(c.getCanalNotificacion()).build();
    }

    @Transactional
    public ClienteDto actualizar(UUID id, ClienteDto dto) {
        Cliente c = unitOfWork.clientes().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        if (dto.getNombre() != null) c.setNombre(dto.getNombre());
        if (dto.getTelefono() != null) c.setTelefono(dto.getTelefono());
        if (dto.getEmail() != null) c.setEmail(dto.getEmail());
        if (dto.getCanalNotificacion() != null) c.setCanalNotificacion(dto.getCanalNotificacion());
        unitOfWork.clientes().save(c);
        unitOfWork.commit();
        return dto;
    }

    @Transactional
    public void eliminar(UUID id) {
        unitOfWork.clientes().deleteById(id);
        unitOfWork.commit();
    }
}
