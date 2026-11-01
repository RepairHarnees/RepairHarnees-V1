package com.repairharnees.application.usecases;

import com.repairharnees.application.dto.ComprobanteLogDto;
import com.repairharnees.application.ports.IUnitOfWork;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ListarLogsUseCase {

    private final IUnitOfWork unitOfWork;

    public ListarLogsUseCase(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional(readOnly = true)
    public List<ComprobanteLogDto> ejecutar() {
        return unitOfWork.comprobanteLogs().findAll().stream().map(l -> ComprobanteLogDto.builder()
                .id(l.getId()).ordenId(l.getOrdenId()).codigoInterno(l.getCodigoInterno())
                .usuarioId(l.getUsuarioId()).usuarioUsername(l.getUsuarioUsername())
                .fechaImpresion(l.getFechaImpresion()).motivo(l.getMotivo()).build()).toList();
    }

    @Transactional(readOnly = true)
    public List<ComprobanteLogDto> porOrden(UUID ordenId) {
        return unitOfWork.comprobanteLogs().findByOrdenId(ordenId).stream().map(l -> ComprobanteLogDto.builder()
                .id(l.getId()).ordenId(l.getOrdenId()).codigoInterno(l.getCodigoInterno())
                .usuarioId(l.getUsuarioId()).usuarioUsername(l.getUsuarioUsername())
                .fechaImpresion(l.getFechaImpresion()).motivo(l.getMotivo()).build()).toList();
    }
}
