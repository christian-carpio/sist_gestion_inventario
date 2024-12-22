package com.uees.mgra.inventario.mapper;

import com.uees.mgra.inventario.modals.entity.Insumo;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("InsumoMapper Tests")
class InsumoMapperTest {

    private final InsumoMapper mapper = new InsumoMapper();

    @Test
    @DisplayName("Mapping Insumo to InsumoDTO")
    void testMapper() {
        Insumo insumo = new Insumo();
        insumo.setId(1L);
        insumo.setCantidad(50);
        insumo.setFechaFabricacion(LocalDate.parse("2023-08-12"));
        insumo.setFechaExpiracion(LocalDate.parse("2023-12-31"));
        insumo.setEsCaducado(false);
        insumo.setTipoInsumoIdPk(new InsumoDetalle(1L, "Insumo Detalle", "Descripcion", new HashSet<>()));

        InsumoDTO expectedDTO = new InsumoDTO(
                1L,
                50,
                LocalDate.parse("2023-08-12"),
                LocalDate.parse("2023-12-31"),
                false,
                1L
        );

        InsumoDTO resultDTO = mapper.apply(insumo);

        assertEquals(expectedDTO, resultDTO);
    }
}
