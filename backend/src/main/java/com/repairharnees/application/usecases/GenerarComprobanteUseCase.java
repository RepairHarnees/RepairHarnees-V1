package com.repairharnees.application.usecases;

import com.repairharnees.application.ports.IPdfGenerator;
import com.repairharnees.application.ports.IUnitOfWork;
import com.repairharnees.domain.entities.Cliente;
import com.repairharnees.domain.entities.ComprobanteLog;
import com.repairharnees.domain.entities.Equipo;
import com.repairharnees.domain.entities.OrdenServicio;
import com.repairharnees.domain.valueobjects.CodigoInterno;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
public class GenerarComprobanteUseCase {

    private final IUnitOfWork unitOfWork;
    private final IPdfGenerator pdfGenerator;

    public GenerarComprobanteUseCase(IUnitOfWork unitOfWork, IPdfGenerator pdfGenerator) {
        this.unitOfWork = unitOfWork;
        this.pdfGenerator = pdfGenerator;
    }

    public byte[] ejecutar(String codigoInterno, java.util.UUID usuarioId, String motivo) {
        OrdenServicio orden = unitOfWork.ordenes().findByCodigoInterno(new CodigoInterno(codigoInterno))
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + codigoInterno));

        Cliente cliente = unitOfWork.clientes().findById(orden.getClienteId())
                .orElseThrow(() -> new IllegalStateException("Cliente no encontrado"));
        Equipo equipo = unitOfWork.equipos().findById(orden.getEquipoId())
                .orElseThrow(() -> new IllegalStateException("Equipo no encontrado"));

        // Log de impresion
        if (usuarioId != null) {
            unitOfWork.usuarios().findById(usuarioId).ifPresent(u -> {
                ComprobanteLog log = ComprobanteLog.builder()
                        .ordenId(orden.getId())
                        .codigoInterno(orden.getCodigoInterno().toString())
                        .usuarioId(u.getId())
                        .usuarioUsername(u.getUsername())
                        .fechaImpresion(java.time.LocalDateTime.now())
                        .motivo(motivo != null ? motivo : "Impresion de comprobante")
                        .build();
                unitOfWork.comprobanteLogs().save(log);
                unitOfWork.commit();
            });
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        pdfGenerator.generarComprobante(orden, cliente, equipo, out);
        return out.toByteArray();
    }
}
