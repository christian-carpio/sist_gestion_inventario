package com.uees.mgra.inventario.service;

import com.uees.mgra.inventario.handler.BadRequestException;
import com.uees.mgra.inventario.handler.NotFoundException;
import com.uees.mgra.inventario.mapper.InsumoDetalleInsumoMapper;
import com.uees.mgra.inventario.modals.dto.InsumoDetalleDTO;
import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import com.uees.mgra.inventario.repository.InsumoDetalleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("InsumoDetalleService Tests")
class InsumoDetalleServiceTest {

    @Mock
    private InsumoDetalleRepository insumoDetalleRepository;

    @Mock
    private InsumoDetalleInsumoMapper insumoDetalleInsumoMapper;

    @InjectMocks
    private InsumoDetalleService insumoDetalleService;

    private InsumoDetalle insumoDetalle;

    private InsumoDetalleDTO insumoDetalleDTO;

    @BeforeEach
    void setUp() {
        insumoDetalle = new InsumoDetalle();
        insumoDetalle.setId(1L);
        insumoDetalle.setNombre("InsumoDetalle1");
        insumoDetalle.setDescripcion("Descripción de InsumoDetalle1");
        insumoDetalleDTO = new InsumoDetalleDTO(1L, "InsumoDetalle1", "Descripción de InsumoDetalle1", new HashSet<>());
    }

    @Test
    @DisplayName("Listar Tipo Insumos")
    void testListarTipoInsumos() {
        List<InsumoDetalle> insumoDetalleList = new ArrayList<>();
        insumoDetalleList.add(insumoDetalle);

        InsumoDetalleInsumoMapper insumoMapper = new InsumoDetalleInsumoMapper();
        List<InsumoDetalleDTO> expectedDtoList = insumoDetalleList.stream()
                .map(insumoMapper)
                .toList();

        when(insumoDetalleRepository.findAll()).thenReturn(insumoDetalleList);
        when(insumoDetalleInsumoMapper.apply(any())).thenReturn(insumoDetalleDTO);

        List<InsumoDetalleDTO> actualDtoList = insumoDetalleService.listarTipoInsumos();

        assertEquals(expectedDtoList.size(), actualDtoList.size());
        assertEquals(expectedDtoList.get(0), actualDtoList.get(0));
    }

    @Test
    @DisplayName("Traer Tipo Insumo - ID nulo")
    void testTraerTipoInsumoIdNulo() {
        assertThrows(BadRequestException.class, () -> insumoDetalleService.traerTipoInsumo(null));
    }

    @Test
    @DisplayName("Traer Tipo Insumo - TipoInsumo no encontrado")
    void testTraerTipoInsumoNoEncontrado() {
        when(insumoDetalleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> insumoDetalleService.traerTipoInsumo(1L));
    }

    @Test
    @DisplayName("Actualizar Tipo Insumo - Datos inválidos")
    void testActualizarTipoInsumoDatosInvalidos() {
        assertThrows(BadRequestException.class, () -> insumoDetalleService.actualizarTipoInsumo(1L, null));
    }

    @Test
    @DisplayName("Registrar Tipo Insumo - DTO nulo")
    void testRegistrarTipoInsumoDtoNulo() {
        assertThrows(BadRequestException.class, () -> insumoDetalleService.registrarTipoInsumo(null));
    }

    @Test
    @DisplayName("Registrar Tipo Insumo - Nombre duplicado")
    void testRegistrarTipoInsumoNombreDuplicado() {
        when(insumoDetalleRepository.findByNombreEqualsIgnoreCase(anyString())).thenReturn(Optional.of(insumoDetalle));

        assertThrows(BadRequestException.class, () -> insumoDetalleService.registrarTipoInsumo(insumoDetalleDTO));
    }

    @Test
    @DisplayName("Eliminar Tipo Insumo - ID nulo")
    void testEliminarTipoInsumoIdNulo() {
        assertThrows(BadRequestException.class, () -> insumoDetalleService.eliminarTipoInsumo(null));
    }

    @Test
    @DisplayName("Eliminar Tipo Insumo - No existe el producto")
    void testEliminarTipoInsumoNoExiste() {
        when(insumoDetalleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> insumoDetalleService.eliminarTipoInsumo(1L));
    }
    @Test
    @DisplayName("Actualizar Tipo Insumo - Tipo de Insumo no existe")
    void testActualizarTipoInsumoNoExiste() {
        when(insumoDetalleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> insumoDetalleService.actualizarTipoInsumo(1L, insumoDetalleDTO));
    }

    @Test
    @DisplayName("Listar Tipo Insumos - Sin registros")
    void testListarTipoInsumosSinRegistros() {
        List<InsumoDetalle> insumoDetalleList = new ArrayList<>();

        when(insumoDetalleRepository.findAll()).thenReturn(insumoDetalleList);

        List<InsumoDetalleDTO> actualDtoList = insumoDetalleService.listarTipoInsumos();

        assertTrue(actualDtoList.isEmpty());
    }

}
