package com.uees.mgra.producto.services;

import com.uees.mgra.producto.handler.BadRequestException;
import com.uees.mgra.producto.handler.NotFoundException;
import com.uees.mgra.producto.mappers.RequerimientoMapper;
import com.uees.mgra.producto.model.dto.RequerimientoDTO;
import com.uees.mgra.producto.model.entity.Producto;
import com.uees.mgra.producto.model.entity.Requerimiento;
import com.uees.mgra.producto.repositories.ProductoRepository;
import com.uees.mgra.producto.repositories.RequerimientoRepository;
import com.uees.mgra.producto.util.RestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RequerimientoServiceTest {

    private RequerimientoService requerimientoService;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private RequerimientoRepository requerimientoRepository;

    private final RequerimientoMapper requerimientoMapper = new RequerimientoMapper();

    @Mock
    private RestService restService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requerimientoService = new RequerimientoService(productoRepository, requerimientoRepository, requerimientoMapper, restService);
        String jsonResponse = """
                {
                  "id": 123,
                  "nombre": "Insumo Detalle",
                  "descripcion": "Descripción del insumo detalle",
                  "insumoDTOSet": [
                    {
                      "id": 1,
                      "cantidad": 50,
                      "fechaFabricacion": "2023-08-12T10:00:00Z",
                      "fechaExpiracion": "2023-12-31T23:59:59Z",
                      "esCaducado": false,
                      "insumoIdpk": 101
                    },
                    {
                      "id": 2,
                      "cantidad": 20,
                      "fechaFabricacion": "2023-06-15T08:30:00Z",
                      "fechaExpiracion": "2023-11-30T23:59:59Z",
                      "esCaducado": true,
                      "insumoIdpk": 102
                    }
                  ]
                }
                                
                """;
        when(restService.invokeRest(
                eq(HttpMethod.GET),
                anyString(),
                any(),
                any(),
                any()
        ))
                .thenReturn(jsonResponse);
    }

    @Test
    @DisplayName("Traer Requerimiento Existente")
    void testTraerRequerimientoExiste() throws BadRequestException, NotFoundException {
        Long id = 1L;
        Requerimiento requerimiento = new Requerimiento();
        when(requerimientoRepository.findById(id)).thenReturn(Optional.of(new Requerimiento(1L, new Producto(), 1L, 2L)));

        RequerimientoDTO result = requerimientoService.traerRequerimiento(id);

        assertNotNull(result);
    }


    @Test
    @DisplayName("Traer Requerimiento No Existente")
    void testTraerRequerimientoNoExiste() {
        Long id = 1L;
        when(requerimientoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> requerimientoService.traerRequerimiento(id));
    }


    @Test
    @DisplayName("Registrar Requerimiento con Insumo Encontrado")
    void testRegistrarRequerimiento() throws BadRequestException, NotFoundException {
        RequerimientoDTO requerimientoDTO = new RequerimientoDTO(1L, 1L, 1L, 10L);

        Producto producto = new Producto();
        producto.setProducto_id(1L);
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));
        Requerimiento requerimiento = new Requerimiento(1L, new Producto(1L, "Hamburguesa", BigDecimal.ONE, "Hamburguesa completa", "userPrueba", new HashSet<>()), 1L, 10L);

        when(requerimientoRepository.save(any())).thenReturn(requerimiento);

        RequerimientoDTO result = requerimientoService.registrarRequerimiento(requerimientoDTO);

        assertNotNull(result);
    }


    @Test
    @DisplayName("Registrar Requerimiento con Insumo No Encontrado")
    void testRegistrarRequerimientoInsumoNoEncontrado() throws BadRequestException, NotFoundException {
        RequerimientoDTO requerimientoDTO = new RequerimientoDTO(1L, 1L, 1L, 10L);

        Producto producto = new Producto();
        producto.setProducto_id(1L);
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));
        String jsonResponse = "{\"details\": \"Insumo no encontrado\"}";
        when(restService.invokeRest(
                eq(HttpMethod.GET),
                anyString(),
                any(),
                any(),
                any()
        ))
                .thenReturn(jsonResponse);
        when(requerimientoRepository.save(any())).thenReturn(new Requerimiento());


        assertThrows(BadRequestException.class, () -> requerimientoService.registrarRequerimiento(requerimientoDTO));
    }


    @Test
    @DisplayName("Actualizar Requerimiento con Insumo Encontrado")
    void testActualizarRequerimiento() throws BadRequestException, NotFoundException {
        Long id = 1L;
        RequerimientoDTO requerimientoDTO = new RequerimientoDTO(1L, 1L, 1L, 20L);

        Requerimiento requerimiento = new Requerimiento(1L, new Producto(1L, "Hamburguesa", BigDecimal.ONE, "Hamburguesa completa", "userPrueba", new HashSet<>()), 1L, 10L);
        when(requerimientoRepository.findById(id)).thenReturn(Optional.of(requerimiento));

        when(requerimientoRepository.save(any())).thenReturn(new Requerimiento());


        RequerimientoDTO result = requerimientoService.actualizarRequerimiento(id, requerimientoDTO);

        assertNotNull(result);

    }


    @Test
    @DisplayName("Actualizar Requerimiento con Insumo No Encontrado")
    void testActualizarRequerimientoInsumoNoEncontrado() throws BadRequestException, NotFoundException {
        Long id = 1L;
        RequerimientoDTO requerimientoDTO = new RequerimientoDTO(1L, 1L, 1L, 20L);

        Requerimiento requerimiento = new Requerimiento();
        when(requerimientoRepository.findById(id)).thenReturn(Optional.of(requerimiento));

        String jsonResponse = "{\"details\": \"Insumo no encontrado\"}";
        when(restService.invokeRest(
                eq(HttpMethod.GET),
                anyString(),
                any(),
                any(),
                any()
        ))
                .thenReturn(jsonResponse);

        when(requerimientoRepository.save(any())).thenReturn(new Requerimiento());

        assertThrows(BadRequestException.class, () -> requerimientoService.actualizarRequerimiento(id, requerimientoDTO));
    }


    @Test
    @DisplayName("Eliminar Requerimiento Existente")
    void testEliminarRequerimientoExiste() throws BadRequestException, NotFoundException {
        Long id = 1L;
        when(requerimientoRepository.findById(id)).thenReturn(Optional.of(new Requerimiento()));

        assertDoesNotThrow(() -> requerimientoService.eliminarRequerimiento(id));
    }

    @Test
    @DisplayName("Eliminar Requerimiento No Existente")
    void testEliminarRequerimientoNoExiste() {
        Long id = 1L;
        when(requerimientoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> requerimientoService.eliminarRequerimiento(id));
    }
}
