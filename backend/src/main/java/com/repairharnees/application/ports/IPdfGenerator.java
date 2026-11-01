package com.repairharnees.application.ports;

import com.repairharnees.domain.entities.Cliente;
import com.repairharnees.domain.entities.Equipo;
import com.repairharnees.domain.entities.OrdenServicio;

import java.io.OutputStream;

public interface IPdfGenerator {
    void generarComprobante(OrdenServicio orden, Cliente cliente, Equipo equipo, OutputStream out);
}
