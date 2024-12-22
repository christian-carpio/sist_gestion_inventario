package com.uees.mgra.inventario.controller;

import com.uees.mgra.inventario.handler.BadRequestException;
import com.uees.mgra.inventario.handler.NotFoundException;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import com.uees.mgra.inventario.modals.dto.InsumoDetalleDTO;
import com.uees.mgra.inventario.service.InsumoDetalleService;
import com.uees.mgra.inventario.service.InsumoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GestorInventarioControllerTest {
    @Mock
    private InsumoDetalleService insumoDetalleService;

    @Mock
    private InsumoService insumoService;

    @InjectMocks
    private GestorInventarioController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Traer Tipo de Insumo Existente")
    void testTraerTipoInsumo() throws BadRequestException, NotFoundException {
       
        InsumoDetalleDTO expectedInsumoDetalle = new InsumoDetalleDTO(1L, "Nombre", "Descripción", new HashSet<>());
        when(insumoDetalleService.traerTipoInsumo(1L)).thenReturn(expectedInsumoDetalle);

      
        ResponseEntity<InsumoDetalleDTO> response = controller.traerTipoInsumo(1L);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedInsumoDetalle, response.getBody());
    }

    @Test
    @DisplayName("Listar Tipos de Insumos")
    void testListarTipoInsumos() {
       
        List<InsumoDetalleDTO> expectedInsumoDetalles = new ArrayList<>();
        when(insumoDetalleService.listarTipoInsumos()).thenReturn(expectedInsumoDetalles);

      
        ResponseEntity<List<InsumoDetalleDTO>> response = controller.listarTipoInsumos();

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedInsumoDetalles, response.getBody());
    }

    @Test
    @DisplayName("Registrar Tipo de Insumo")
    void testRegistrarTipoInsumo() throws BadRequestException, NotFoundException {
       
        InsumoDetalleDTO inputInsumoDetalle = new InsumoDetalleDTO(null, "Nombre", "Descripción", new HashSet<>());
        InsumoDetalleDTO expectedInsumoDetalle = new InsumoDetalleDTO(1L, "Nombre", "Descripción", new HashSet<>());
        when(insumoDetalleService.registrarTipoInsumo(inputInsumoDetalle)).thenReturn(expectedInsumoDetalle);

      
        ResponseEntity<InsumoDetalleDTO> response = controller.registrarTipoInsumo(inputInsumoDetalle);

       
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedInsumoDetalle, response.getBody());
    }

    @Test
    @DisplayName("Actualizar Tipo de Insumo Existente")
    void testActualizarTipoInsumo() throws BadRequestException, NotFoundException {
       
        Long id = 1L;
        InsumoDetalleDTO inputInsumoDetalle = new InsumoDetalleDTO(id, "Nuevo Nombre", "Nueva Descripción", new HashSet<>());
        InsumoDetalleDTO expectedInsumoDetalle = new InsumoDetalleDTO(id, "Nuevo Nombre", "Nueva Descripción", new HashSet<>());
        when(insumoDetalleService.actualizarTipoInsumo(id, inputInsumoDetalle)).thenReturn(expectedInsumoDetalle);

      
        ResponseEntity<InsumoDetalleDTO> response = controller.actualizarTipoInsumo(id, inputInsumoDetalle);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedInsumoDetalle, response.getBody());
    }

    @Test
    @DisplayName("Eliminar Tipo de Insumo Existente")
    void testEliminarTipoInsumo() throws BadRequestException, NotFoundException {
       
        Long id = 1L;

      
        ResponseEntity<Void> response = controller.eliminarTipoInsumo(id);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(insumoDetalleService, times(1)).eliminarTipoInsumo(id);
    }

    @Test
    @DisplayName("Traer Insumo Existente")
    void testTraerInsumo() throws BadRequestException, NotFoundException {
       
        Long id = 1L;
        InsumoDTO expectedInsumo = new InsumoDTO(id, 10, LocalDate.now(), LocalDate.now().plusDays(30), false, 2L);
        when(insumoService.traerInsumo(id)).thenReturn(expectedInsumo);

      
        ResponseEntity<InsumoDTO> response = controller.traerInsumo(id);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedInsumo, response.getBody());
    }

    @Test
    @DisplayName("Listar Insumos")
    void testListarInsumo() {
       
        List<InsumoDTO> expectedInsumos = new ArrayList<>();
        when(insumoService.listarInsumo()).thenReturn(expectedInsumos);

      
        ResponseEntity<List<InsumoDTO>> response = controller.listarInsumo();

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedInsumos, response.getBody());
    }

    @Test
    @DisplayName("Registrar Insumo")
    void testRegistrarInsumo() throws BadRequestException, NotFoundException {
       
        InsumoDTO inputInsumo = new InsumoDTO(null, 10, LocalDate.now(), LocalDate.now().plusDays(30), false, 2L);
        InsumoDTO expectedInsumo = new InsumoDTO(1L, 10, LocalDate.now(), LocalDate.now().plusDays(30), false, 2L);
        when(insumoService.registrarInsumo(inputInsumo)).thenReturn(expectedInsumo);

      
        ResponseEntity<InsumoDTO> response = controller.registrarInsumo(inputInsumo);

       
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedInsumo, response.getBody());
    }

    @Test
    @DisplayName("Actualizar Insumo Existente")
    void testActualizarInsumo() throws BadRequestException, NotFoundException {
       
        Long id = 1L;
        InsumoDTO inputInsumo = new InsumoDTO(id, 15, LocalDate.now(), LocalDate.now().plusDays(60), true, 3L);
        InsumoDTO expectedInsumo = new InsumoDTO(id, 15, LocalDate.now(), LocalDate.now().plusDays(60), true, 3L);
        when(insumoService.actualizarInsumo(id, inputInsumo)).thenReturn(expectedInsumo);

      
        ResponseEntity<InsumoDTO> response = controller.actualizarInsumo(id, inputInsumo);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedInsumo, response.getBody());
    }

    @Test
    @DisplayName("Eliminar Insumo Existente")
    void testEliminarInsumo() throws BadRequestException, NotFoundException {
       
        Long id = 1L;

      
        ResponseEntity<Void> response = controller.eliminarInsumo(id);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(insumoService, times(1)).eliminarInsumo(id);
    }

    @Test
    @DisplayName("Listar Detalles de Insumos")
    void testListarInsumoDetalles() {
       
        List<InsumoDetalleDTO> expectedInsumoDetalles = new ArrayList<>();
        when(insumoDetalleService.listarTipoInsumos()).thenReturn(expectedInsumoDetalles);

      
        ResponseEntity<List<InsumoDetalleDTO>> response = controller.listarTipoInsumos();

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedInsumoDetalles, response.getBody());
    }
}
