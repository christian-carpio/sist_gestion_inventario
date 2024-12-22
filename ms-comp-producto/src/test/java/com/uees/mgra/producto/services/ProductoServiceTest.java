package com.uees.mgra.producto.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.uees.mgra.producto.handler.BadRequestException;
import com.uees.mgra.producto.handler.NotFoundException;
import com.uees.mgra.producto.mappers.ProductoRequerimientosMapper;
import com.uees.mgra.producto.model.dto.ProductoDTO;
import com.uees.mgra.producto.model.entity.Producto;
import com.uees.mgra.producto.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoRequerimientosMapper productoRequerimientosMapper;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Traer Producto por Id existente")
    public void testTraerProductoPorIdExistente() throws BadRequestException, NotFoundException {
        Long productId = 1L;
        Producto producto = new Producto();
        producto.setProducto_id(productId);
        when(productoRepository.findById(productId)).thenReturn(Optional.of(producto));

        ProductoDTO productoDTO = new ProductoDTO(productId, "Producto de prueba", BigDecimal.valueOf(100.0), "Descripción de prueba", "Usuario", null);
        when(productoRequerimientosMapper.apply(producto)).thenReturn(productoDTO);

        ProductoDTO result = productoService.traerProducto(productId);

        assertNotNull(result);
        assertEquals(productoDTO, result);
    }

    @Test
    @DisplayName("Listar Productos")
    public void testListarProductos() {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto());
        productos.add(new Producto());

        when(productoRepository.findAll()).thenReturn(productos);

        List<ProductoDTO> result = productoService.listarProductos();

        assertNotNull(result);
        assertEquals(productos.size(), result.size());
    }

    @Test
    @DisplayName("Registrar Producto")
    public void testRegistrarProducto() throws BadRequestException, NotFoundException {
        ProductoDTO productoDTO = new ProductoDTO(null, "Nuevo Producto", BigDecimal.valueOf(50.0), "Descripción del nuevo producto", "Usuario", null);
        Producto productoMock = new Producto();
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);
        when(productoRequerimientosMapper.apply(productoMock)).thenReturn(productoDTO);
        ProductoDTO result = productoService.registrarProducto(productoDTO);
        assertNotNull(result);
        assertEquals(productoDTO, result);
    }


    @Test
    @DisplayName("Actualizar Producto por Id existente")
    public void testActualizarProductoPorIdExistente() throws BadRequestException, NotFoundException {
        Long productId = 1L;
        ProductoDTO productoDTO = new ProductoDTO(productId, "Producto actualizado", BigDecimal.valueOf(75.0), "Descripción actualizada", "Usuario Actualizado", null);
        Producto productoMock = new Producto();
        when(productoRepository.findById(productId)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);
        when(productoRequerimientosMapper.apply(productoMock)).thenReturn(productoDTO);

        ProductoDTO result = productoService.actualizarProducto(productId, productoDTO);

        assertNotNull(result);
        assertEquals(productoDTO, result);
    }

    @Test
    @DisplayName("Eliminar Producto por Id existente")
    public void testEliminarProductoPorIdExistente() throws BadRequestException, NotFoundException {
        Long productId = 1L;
        Producto productoMock = new Producto();
        when(productoRepository.findById(productId)).thenReturn(Optional.of(productoMock));

        assertDoesNotThrow(() -> productoService.eliminarProducto(productId));
        verify(productoRepository, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Eliminar Producto por Id inexistente")
    public void testEliminarProductoPorIdInexistente() throws BadRequestException, NotFoundException {
        Long productId = 1L;
        when(productoRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productoService.eliminarProducto(productId));
    }
}
