package com.repairharnees.application.usecases;

import com.repairharnees.application.dto.UsuarioDto;
import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.entities.Usuario;
import com.repairharnees.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioCrudUseCase {

    private final IUnitOfWork unitOfWork;
    private final PasswordEncoder passwordEncoder;

    public UsuarioCrudUseCase(IUnitOfWork unitOfWork, PasswordEncoder passwordEncoder) {
        this.unitOfWork = unitOfWork;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioDto crear(UsuarioDto dto) {
        if (unitOfWork.usuarios().existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("El username ya existe: " + dto.getUsername());
        }
        Usuario u = Usuario.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nombre(dto.getNombre())
                .rol(dto.getRol())
                .activo(dto.isActivo())
                .build();
        u = unitOfWork.usuarios().save(u);
        unitOfWork.commit();
        dto.setId(u.getId());
        dto.setPassword(null);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> listar() {
        return unitOfWork.usuarios().findAll().stream().map(u -> UsuarioDto.builder()
                .id(u.getId()).username(u.getUsername()).nombre(u.getNombre())
                .rol(u.getRol()).activo(u.isActivo()).build()).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDto obtener(UUID id) {
        Usuario u = unitOfWork.usuarios().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        return UsuarioDto.builder().id(u.getId()).username(u.getUsername()).nombre(u.getNombre())
                .rol(u.getRol()).activo(u.isActivo()).build();
    }

    @Transactional
    public UsuarioDto actualizar(UUID id, UsuarioDto dto) {
        Usuario u = unitOfWork.usuarios().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        if (dto.getNombre() != null) u.setNombre(dto.getNombre());
        if (dto.getRol() != null) u.setRol(dto.getRol());
        u.setActivo(dto.isActivo());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        unitOfWork.usuarios().save(u);
        unitOfWork.commit();
        dto.setId(u.getId());
        dto.setPassword(null);
        return dto;
    }

    @Transactional
    public void eliminar(UUID id) {
        unitOfWork.usuarios().deleteById(id);
        unitOfWork.commit();
    }
}
