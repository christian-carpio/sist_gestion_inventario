package com.uees.mgra.producto.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.uees.mgra.producto.model.dto.ProductoDTO;
import com.uees.mgra.producto.model.entity.Producto;
import com.uees.mgra.producto.model.entity.Requerimiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

public class ProductoRequerimientosMapperTest {

    @Mock
    private Producto productoMock;

    @InjectMocks
    private ProductoRequerimientosMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);


        when(productoMock.getProducto_id()).thenReturn(1L);
        when(productoMock.getNombre()).thenReturn("Producto de prueba");
        when(productoMock.getDescripcion()).thenReturn("Descripción de prueba");
        when(productoMock.getUserCreacion()).thenReturn("Usuario");
        when(productoMock.getPrecio()).thenReturn(new BigDecimal("100.0"));
        Set<Requerimiento> requerimientoSet = new HashSet<>();
        requerimientoSet.add(new Requerimiento(1L, new Producto(), 1L, 10L));
        requerimientoSet.add(new Requerimiento(2L, new Producto(), 2L, 10L));
        when(productoMock.getRequerimientoSet()).thenReturn(requerimientoSet);
    }

    @Test
    @DisplayName("Mapping Producto with Requerimiento to ProductoDTO")
    public void testProductoToProductoDTO() {
        ProductoDTO productoDTO = mapper.apply(productoMock);

        assertEquals(1L, productoDTO.id());
        assertEquals("Producto de prueba", productoDTO.nombre());
        assertEquals(new BigDecimal("100.0"), productoDTO.precio());
        assertEquals("Descripción de prueba", productoDTO.descripcion());
        assertEquals("Usuario", productoDTO.nombreCreador());


        assertEquals(2, productoDTO.requerimientoDTOSet().size());
    }
}
