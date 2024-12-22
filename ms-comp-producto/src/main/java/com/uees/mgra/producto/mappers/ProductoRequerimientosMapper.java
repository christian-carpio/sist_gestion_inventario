package com.uees.mgra.producto.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.uees.mgra.producto.model.dto.ProductoDTO;
import com.uees.mgra.producto.model.dto.RequerimientoDTO;
import com.uees.mgra.producto.model.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoRequerimientosMapper implements Function<Producto, ProductoDTO> {

    @Override
    public ProductoDTO apply(Producto producto) {
        Stream<RequerimientoDTO> requerimientoDTOStream = producto.getRequerimientoSet().stream().map(req -> {
            return new RequerimientoDTO(req.getId(), req.getProducto_id().getProducto_id(), req.getTipoInsumoIdFK(), req.getCantidad());
        });

        return new ProductoDTO(producto.getProducto_id(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(), producto.getUserCreacion(),requerimientoDTOStream.collect(Collectors.toSet()));
    }
}

