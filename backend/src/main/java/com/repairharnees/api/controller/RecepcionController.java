package com.repairharnees.api.controller;

import com.repairharnees.application.dto.RecepcionRequest;
import com.repairharnees.application.dto.RecepcionResponse;
import com.repairharnees.application.usecases.ActualizarOrdenUseCase;
import com.repairharnees.application.usecases.EliminarOrdenUseCase;
import com.repairharnees.application.usecases.GenerarComprobanteUseCase;
import com.repairharnees.application.usecases.ListarOrdenesUseCase;
import com.repairharnees.application.usecases.ObtenerOrdenUseCase;
import com.repairharnees.application.usecases.RegistrarRecepcionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Recepcion", description = "CRUD de ordenes de servicio (incremento funcional Sprint 1)")
public class RecepcionController {

    private final RegistrarRecepcionUseCase registrarRecepcion;
    private final GenerarComprobanteUseCase generarComprobante;
    private final ListarOrdenesUseCase listarOrdenes;
    private final ObtenerOrdenUseCase obtenerOrden;
    private final ActualizarOrdenUseCase actualizarOrden;
    private final EliminarOrdenUseCase eliminarOrden;

    public RecepcionController(RegistrarRecepcionUseCase registrarRecepcion,
                               GenerarComprobanteUseCase generarComprobante,
                               ListarOrdenesUseCase listarOrdenes,
                               ObtenerOrdenUseCase obtenerOrden,
                               ActualizarOrdenUseCase actualizarOrden,
                               EliminarOrdenUseCase eliminarOrden) {
        this.registrarRecepcion = registrarRecepcion;
        this.generarComprobante = generarComprobante;
        this.listarOrdenes = listarOrdenes;
        this.obtenerOrden = obtenerOrden;
        this.actualizarOrden = actualizarOrden;
        this.eliminarOrden = eliminarOrden;
    }

    @PostMapping("/recepciones")
    @Operation(summary = "Crear: registrar la recepcion de un equipo")
    public ResponseEntity<RecepcionResponse> registrar(@Valid @RequestBody RecepcionRequest request) {
        return ResponseEntity.ok(registrarRecepcion.ejecutar(request));
    }

    @GetMapping("/ordenes")
    @Operation(summary = "Leer: listar todas las ordenes de servicio")
    public ResponseEntity<List<RecepcionResponse>> listar() {
        return ResponseEntity.ok(listarOrdenes.ejecutar());
    }

    @GetMapping("/ordenes/{codigoInterno}")
    @Operation(summary = "Leer: obtener una orden por codigo interno")
    public ResponseEntity<RecepcionResponse> obtener(@PathVariable String codigoInterno) {
        return ResponseEntity.ok(obtenerOrden.ejecutar(codigoInterno));
    }

    @PutMapping("/ordenes/{codigoInterno}")
    @Operation(summary = "Actualizar: modificar observaciones/datos de una orden")
    public ResponseEntity<RecepcionResponse> actualizar(@PathVariable String codigoInterno,
                                                       @Valid @RequestBody RecepcionRequest request) {
        return ResponseEntity.ok(actualizarOrden.ejecutar(codigoInterno, request));
    }

    @DeleteMapping("/ordenes/{codigoInterno}")
    @Operation(summary = "Eliminar: borrar una orden de servicio")
    public ResponseEntity<Void> eliminar(@PathVariable String codigoInterno) {
        eliminarOrden.ejecutar(codigoInterno);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ordenes/{codigoInterno}/comprobante")
    @Operation(summary = "Emitir el comprobante de recepcion en PDF (registra log de impresion)")
    public ResponseEntity<byte[]> comprobante(@PathVariable String codigoInterno,
                                             @RequestParam(required = false) java.util.UUID usuarioId,
                                             @RequestParam(required = false) String motivo) {
        byte[] pdf = generarComprobante.ejecutar(codigoInterno, usuarioId, motivo);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprobante-" + codigoInterno + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
