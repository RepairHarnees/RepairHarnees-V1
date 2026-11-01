package com.repairharnees.infrastructure.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.repairharnees.application.ports.IPdfGenerator;
import com.repairharnees.domain.entities.Cliente;
import com.repairharnees.domain.entities.Equipo;
import com.repairharnees.domain.entities.OrdenServicio;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class ComprobantePdfGenerator implements IPdfGenerator {

    @Override
    public void generarComprobante(OrdenServicio orden, Cliente cliente, Equipo equipo, OutputStream out) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11);

            Paragraph title = new Paragraph("Comprobante de Recepcion", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Codigo interno: " + orden.getCodigoInterno(), headerFont));
            document.add(new Paragraph("Fecha de recepcion: " + orden.getFechaRecepcion()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), normalFont));
            document.add(new Paragraph("Estado: " + orden.getEstado(), normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Datos del cliente", headerFont));
            document.add(new Paragraph("Nombre: " + cliente.getNombre(), normalFont));
            document.add(new Paragraph("Telefono: " + (cliente.getTelefono() != null ? cliente.getTelefono() : "-"), normalFont));
            document.add(new Paragraph("Email: " + (cliente.getEmail() != null ? cliente.getEmail() : "-"), normalFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Datos del equipo", headerFont));
            document.add(new Paragraph("Marca: " + equipo.getMarca(), normalFont));
            document.add(new Paragraph("Modelo: " + equipo.getModelo(), normalFont));
            document.add(new Paragraph("Numero de serie: " + (equipo.getNumeroSerie() != null ? equipo.getNumeroSerie() : "-"), normalFont));
            if (equipo.getDescripcion() != null) {
                document.add(new Paragraph("Descripcion: " + equipo.getDescripcion(), normalFont));
            }
            document.add(new Paragraph(" "));

            if (orden.getObservaciones() != null) {
                document.add(new Paragraph("Observaciones: " + orden.getObservaciones(), normalFont));
                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph("Solucion de Oficina Diaz - Sistema de Gestion de Taller", normalFont));
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }
}
