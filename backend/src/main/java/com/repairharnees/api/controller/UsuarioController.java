package com.repairharnees.api.controller;

import com.repairharnees.application.dto.UsuarioDto;
import com.repairharnees.application.usecases.UsuarioCrudUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "CRUD de usuarios (solo recepcionistas y administradores)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioCrudUseCase usuarioCrud;

    public UsuarioController(UsuarioCrudUseCase usuarioCrud) {
        this.usuarioCrud = usuarioCrud;
    }

    @PostMapping
    @Operation(summary = "Crear usuario")
    public ResponseEntity<UsuarioDto> crear(@Valid @RequestBody UsuarioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCrud.crear(dto));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios")
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioCrud.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por id")
    public ResponseEntity<UsuarioDto> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioCrud.obtener(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<UsuarioDto> actualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioDto dto) {
        return ResponseEntity.ok(usuarioCrud.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        usuarioCrud.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
