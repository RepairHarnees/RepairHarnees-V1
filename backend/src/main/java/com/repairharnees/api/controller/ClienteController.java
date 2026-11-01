package com.repairharnees.api.controller;

import com.repairharnees.application.dto.ClienteDto;
import com.repairharnees.application.dto.RecepcionPorClienteRequest;
import com.repairharnees.application.dto.RecepcionResponse;
import com.repairharnees.application.usecases.ClienteCrudUseCase;
import com.repairharnees.application.usecases.RegistrarRecepcionPorClienteUseCase;
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
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "CRUD de clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    private final ClienteCrudUseCase clienteCrud;
    private final RegistrarRecepcionPorClienteUseCase registrarPorCliente;

    public ClienteController(ClienteCrudUseCase clienteCrud, RegistrarRecepcionPorClienteUseCase registrarPorCliente) {
        this.clienteCrud = clienteCrud;
        this.registrarPorCliente = registrarPorCliente;
    }

    @PostMapping
    @Operation(summary = "Crear cliente")
    public ResponseEntity<ClienteDto> crear(@Valid @RequestBody ClienteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCrud.crear(dto));
    }

    @GetMapping
    @Operation(summary = "Listar clientes")
    public ResponseEntity<List<ClienteDto>> listar() {
        return ResponseEntity.ok(clienteCrud.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por id")
    public ResponseEntity<ClienteDto> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteCrud.obtener(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente")
    public ResponseEntity<ClienteDto> actualizar(@PathVariable UUID id, @Valid @RequestBody ClienteDto dto) {
        return ResponseEntity.ok(clienteCrud.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        clienteCrud.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clienteId}/recepciones")
    @Operation(summary = "Agregar una recepcion a un cliente existente")
    public ResponseEntity<RecepcionResponse> recepcion(@PathVariable UUID clienteId,
                                                      @Valid @RequestBody RecepcionPorClienteRequest request) {
        return ResponseEntity.ok(registrarPorCliente.ejecutar(clienteId, request.getEquipo(), request.getObservaciones()));
    }
}
