package com.uees.mgra.producto.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uees.mgra.producto.model.dto.RequerimientoDTO;
import com.uees.mgra.producto.model.entity.Producto;
import com.uees.mgra.producto.model.entity.Requerimiento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class RequerimientoMapperTest {

    @InjectMocks
    private RequerimientoMapper mapper;

    @Test
    @DisplayName("Mapping Requerimiento to RequerimientoDTO")
    public void testRequerimientoToRequerimientoDTO() {
        Producto producto = new Producto();
        producto.setProducto_id(1L);
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setProducto_id(producto);
        requerimiento.setTipoInsumoIdFK(2L);
        requerimiento.setCantidad(10L);

        RequerimientoDTO requerimientoDTO = mapper.apply(requerimiento);

        assertEquals(1L, requerimientoDTO.productoId());
        assertEquals(2L, requerimientoDTO.insumoId());
        assertEquals(10L, requerimientoDTO.cantidad());
    }
}
