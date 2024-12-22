package com.uees.mgra.producto.mappers;

import com.uees.mgra.producto.model.dto.RequerimientoDTO;
import com.uees.mgra.producto.model.entity.Requerimiento;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequerimientoMapper implements Function<Requerimiento, RequerimientoDTO> {
    @Override
    public RequerimientoDTO apply(Requerimiento requerimiento) {
        return new RequerimientoDTO(requerimiento.getProducto_id().getProducto_id(),requerimiento.getProducto_id().getProducto_id(), requerimiento.getTipoInsumoIdFK(), requerimiento.getCantidad());
    }
}
