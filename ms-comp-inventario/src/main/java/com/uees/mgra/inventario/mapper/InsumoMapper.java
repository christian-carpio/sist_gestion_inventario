package com.uees.mgra.inventario.mapper;

import com.uees.mgra.inventario.modals.entity.Insumo;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InsumoMapper implements Function<Insumo, InsumoDTO> {

    @Override
    public InsumoDTO apply(Insumo insumo) {
        return new InsumoDTO(
            insumo.getId(),
            insumo.getCantidad(),
            insumo.getFechaFabricacion(),
            insumo.getFechaExpiracion(),
            insumo.isEsCaducado(),
            insumo.getTipoInsumoIdPk().getId()
        );
    }
}
