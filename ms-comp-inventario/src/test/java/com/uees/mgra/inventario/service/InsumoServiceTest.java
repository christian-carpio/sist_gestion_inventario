package com.uees.mgra.inventario.service;

import com.uees.mgra.inventario.handler.BadRequestException;
import com.uees.mgra.inventario.handler.NotFoundException;
import com.uees.mgra.inventario.mapper.InsumoMapper;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import com.uees.mgra.inventario.modals.entity.Insumo;
import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import com.uees.mgra.inventario.repository.InsumoRepository;
import com.uees.mgra.inventario.repository.InsumoDetalleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("InsumoService Tests")
class InsumoServiceTest {

    @InjectMocks
    private InsumoService insumoService;

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private InsumoDetalleRepository insumoDetalleRepository;

    @Mock
    private InsumoMapper insumoMapper;

    private Insumo insumo;
    private InsumoDetalle insumoDetalle;

    @BeforeEach
    void setUp() {
        insumo = new Insumo();
        insumo.setId(1L);
        insumo.setCantidad(50);
        insumo.setFechaFabricacion(LocalDate.now());
        insumo.setFechaExpiracion(LocalDate.now().plusMonths(6));
        insumo.setEsCaducado(false);
        insumo.setTipoInsumoIdPk(new InsumoDetalle());

        insumoDetalle = new InsumoDetalle();
        insumoDetalle.setId(1L);

        when(insumoDetalleRepository.findById(anyLong())).thenReturn(Optional.of(insumoDetalle));
        when(insumoRepository.findById(anyLong())).thenReturn(Optional.of(insumo));
    }

    @Test
    @DisplayName("Traer Insumo")
    void testTraerInsumo() throws BadRequestException, NotFoundException {
        InsumoDTO expectedDTO = new InsumoDTO(1L, 50, LocalDate.now(), LocalDate.now().plusMonths(6), false, 1L);
        when(insumoMapper.apply(any())).thenReturn(expectedDTO);

        InsumoDTO result = insumoService.traerInsumo(1L);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    @DisplayName("Listar Insumo")
    void testListarInsumo() {
        List<Insumo> insumoList = new ArrayList<>();
        insumoList.add(insumo);
        when(insumoRepository.findAll()).thenReturn(insumoList);

        InsumoDTO expectedDTO = new InsumoDTO(1L, 50, LocalDate.now(), LocalDate.now().plusMonths(6), false, 1L);
        when(insumoMapper.apply(any())).thenReturn(expectedDTO);

        List<InsumoDTO> result = insumoService.listarInsumo();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(expectedDTO, result.get(0));
    }

    @Test
    @DisplayName("Traer Insumo - ID nulo")
    void testTraerInsumoIdNulo() {
        assertThrows(BadRequestException.class, () -> insumoService.traerInsumo(null));
    }

    @Test
    @DisplayName("Traer Insumo - Insumo no encontrado")
    void testTraerInsumoNoEncontrado() {
        when(insumoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> insumoService.traerInsumo(1L));
    }

    @Test
    @DisplayName("Registrar Insumo - Datos inválidos")
    void testRegistrarInsumoDatosInvalidos() {
        InsumoDTO insumoDTO = new InsumoDTO(1L, -10, LocalDate.now(), LocalDate.now().minusDays(1), false, 1L);

        assertThrows(BadRequestException.class, () -> insumoService.registrarInsumo(insumoDTO));
    }

    @Test
    @DisplayName("Registrar Insumo")
    void testRegistrarInsumo() throws BadRequestException, NotFoundException {
        InsumoDTO insumoDTO = new InsumoDTO(1L, 50, LocalDate.now(), LocalDate.now().plusMonths(6), false, 1L);
        when(insumoMapper.apply(any())).thenReturn(insumoDTO);

        when(insumoRepository.save(any())).thenReturn(insumo);

        InsumoDTO result = insumoService.registrarInsumo(insumoDTO);

        assertNotNull(result);
        assertEquals(insumoDTO, result);
    }

    @Test
    @DisplayName("Actualizar Insumo")
    void testActualizarInsumo() throws NotFoundException, BadRequestException {
        InsumoDTO insumoDTO = new InsumoDTO(1L, 60, LocalDate.now(), LocalDate.now().plusMonths(12), true, 1L);
        when(insumoMapper.apply(any())).thenReturn(insumoDTO);

        when(insumoRepository.save(any())).thenReturn(insumo);

        InsumoDTO result = insumoService.actualizarInsumo(1L, insumoDTO);

        assertNotNull(result);
        assertEquals(insumoDTO, result);
    }

    @Test
    @DisplayName("Eliminar Insumo")
    void testEliminarInsumo() throws BadRequestException, NotFoundException {
        assertDoesNotThrow(() -> insumoService.eliminarInsumo(1L));

        verify(insumoRepository, times(1)).delete(any());
    }
}
