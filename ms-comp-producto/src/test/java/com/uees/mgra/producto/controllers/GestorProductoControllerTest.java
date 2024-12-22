package com.uees.mgra.producto.controllers;

import com.uees.mgra.producto.handler.BadRequestException;
import com.uees.mgra.producto.handler.NotFoundException;
import com.uees.mgra.producto.model.dto.ProductoDTO;
import com.uees.mgra.producto.model.dto.RequerimientoDTO;
import com.uees.mgra.producto.services.ProductoService;
import com.uees.mgra.producto.services.RequerimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GestorProductoControllerTest {

    @InjectMocks
    private GestorProductoController controller;

    @Mock
    private ProductoService productoService;

    @Mock
    private RequerimientoService requerimientoService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testTraerProducto() throws BadRequestException, NotFoundException {
        Long id = 1L;
        ProductoDTO expectedDTO = new ProductoDTO(1L, "Producto", BigDecimal.valueOf(10L), "Descripción", "Usuario", new HashSet<>());
        when(productoService.traerProducto(id)).thenReturn(expectedDTO);

        ResponseEntity<ProductoDTO> response = controller.traerProducto(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    void testListarProductos() {
        List<ProductoDTO> expectedList = new ArrayList<>();
        expectedList.add(new ProductoDTO(1L, "Producto1", BigDecimal.valueOf(10L), "Descripción1", "Usuario1", new HashSet<>()));
        expectedList.add(new ProductoDTO(2L, "Producto2", BigDecimal.valueOf(20L), "Descripción2", "Usuario1", new HashSet<>()));
        when(productoService.listarProductos()).thenReturn(expectedList);

        ResponseEntity<List<ProductoDTO>> response = controller.listarProductos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void testRegistrarProducto() throws BadRequestException, NotFoundException {
        ProductoDTO inputDTO = new ProductoDTO(null, "ProductoNuevo", BigDecimal.valueOf(30L), "Descripción", "Usuario1", new HashSet<>());
        ProductoDTO expectedDTO = new ProductoDTO(1L, "ProductoNuevo", BigDecimal.valueOf(30L), "Descripción", "Usuario1", new HashSet<>());
        when(productoService.registrarProducto(any())).thenReturn(expectedDTO);

        ResponseEntity<ProductoDTO> response = controller.registrarProducto(inputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    void testActualizarProducto() throws BadRequestException, NotFoundException {
        Long id = 1L;
        ProductoDTO inputDTO = new ProductoDTO(null, "ProductoActualizado", BigDecimal.valueOf(30L), "Descripción actualizada", "Usuario", new HashSet<>());
        ProductoDTO expectedDTO = new ProductoDTO(id, "ProductoActualizado", BigDecimal.valueOf(30L), "Descripción actualizada", "Usuario", new HashSet<>());
        when(productoService.actualizarProducto(eq(id), any())).thenReturn(expectedDTO);

        ResponseEntity<ProductoDTO> response = controller.actualizarProducto(id, inputDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    void testEliminarProducto() throws BadRequestException, NotFoundException {
        Long id = 1L;

        ResponseEntity<Void> response = controller.eliminarProducto(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testTraerRequerimiento() throws BadRequestException, NotFoundException {
        Long id = 1L;
        RequerimientoDTO expectedDTO = new RequerimientoDTO(1L, 1L, 1L, 10L);
        when(requerimientoService.traerRequerimiento(id)).thenReturn(expectedDTO);

        ResponseEntity<RequerimientoDTO> response = controller.traerRequerimiento(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    void testListarRequerimientos() {
        List<RequerimientoDTO> expectedList = new ArrayList<>();
        expectedList.add(new RequerimientoDTO(1L, 1L, 1L, 10L));
        expectedList.add(new RequerimientoDTO(2L, 2L, 2L, 20L));
        when(requerimientoService.listarRequerimiento()).thenReturn(expectedList);

        ResponseEntity<List<RequerimientoDTO>> response = controller.listarRequerimiento();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void testRegistrarRequerimiento() throws BadRequestException, NotFoundException {
        RequerimientoDTO inputDTO = new RequerimientoDTO(1L, 1L, 1L, 10L);
        RequerimientoDTO expectedDTO = new RequerimientoDTO(1L, 1L, 1L, 10L);
        when(requerimientoService.registrarRequerimiento(any())).thenReturn(expectedDTO);

        ResponseEntity<RequerimientoDTO> response = controller.registrarProducto(inputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    void testActualizarRequerimiento() throws BadRequestException, NotFoundException {
        Long id = 1L;
        RequerimientoDTO inputDTO = new RequerimientoDTO(1L, 1L, 1L, 20L);
        RequerimientoDTO expectedDTO = new RequerimientoDTO(id, 1L, 1L, 20L);
        when(requerimientoService.actualizarRequerimiento(eq(id), any())).thenReturn(expectedDTO);

        ResponseEntity<RequerimientoDTO> response = controller.actualizarRequerimiento(id, inputDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    void testEliminarRequerimiento() throws BadRequestException, NotFoundException {
        Long id = 1L;

        ResponseEntity<Void> response = controller.eliminarRequerimiento(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
