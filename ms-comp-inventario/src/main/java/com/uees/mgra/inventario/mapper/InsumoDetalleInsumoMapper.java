package com.uees.mgra.inventario.mapper;

import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import com.uees.mgra.inventario.modals.dto.InsumoDetalleDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InsumoDetalleInsumoMapper implements Function<InsumoDetalle, InsumoDetalleDTO> {

    @Override
    public InsumoDetalleDTO apply(InsumoDetalle insumoDetalle) {
        Stream<InsumoDTO> insumoDTOStream = insumoDetalle.getInsumoSet().stream().map(req -> {
            return new InsumoDTO(req.getId(), req.getCantidad(), req.getFechaFabricacion(), req.getFechaExpiracion(), req.isEsCaducado(), req.getTipoInsumoIdPk().getId());
        });

        return new InsumoDetalleDTO(insumoDetalle.getId(), insumoDetalle.getNombre(), insumoDetalle.getDescripcion(), insumoDTOStream.collect(Collectors.toSet()));
    }
}

