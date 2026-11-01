package com.repairharnees.api.controller;

import com.repairharnees.application.dto.ComprobanteLogDto;
import com.repairharnees.application.usecases.ListarLogsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Logs", description = "Auditoria de impresion de comprobantes")
@SecurityRequirement(name = "bearerAuth")
public class LogController {

    private final ListarLogsUseCase listarLogs;

    public LogController(ListarLogsUseCase listarLogs) {
        this.listarLogs = listarLogs;
    }

    @GetMapping("/comprobantes")
    @Operation(summary = "Listar todos los logs de impresion de comprobantes")
    public ResponseEntity<List<ComprobanteLogDto>> listar() {
        return ResponseEntity.ok(listarLogs.ejecutar());
    }

    @GetMapping("/comprobantes/orden/{ordenId}")
    @Operation(summary = "Logs de impresion de una orden")
    public ResponseEntity<List<ComprobanteLogDto>> porOrden(@PathVariable UUID ordenId) {
        return ResponseEntity.ok(listarLogs.porOrden(ordenId));
    }
}
