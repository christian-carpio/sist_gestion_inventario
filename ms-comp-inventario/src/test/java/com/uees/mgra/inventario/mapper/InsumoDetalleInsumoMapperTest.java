package com.uees.mgra.inventario.mapper;

import com.uees.mgra.inventario.modals.dto.InsumoDetalleDTO;
import com.uees.mgra.inventario.modals.dto.InsumoDTO;
import com.uees.mgra.inventario.modals.entity.Insumo;
import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsumoDetalleInsumoMapperTest {

    private final InsumoDetalleInsumoMapper mapper = new InsumoDetalleInsumoMapper();

    @Test
    @DisplayName("Mapping InsumoDetalle to InsumoDetalleDTO")
    void testMapper() {

        InsumoDetalle insumoDetalle = new InsumoDetalle();
        insumoDetalle.setId(1L);
        insumoDetalle.setNombre("Insumo Detalle");
        insumoDetalle.setDescripcion("Descripción del insumo detalle");

        Insumo insumo1 = new Insumo();
        insumo1.setId(1L);
        insumo1.setCantidad(50);
        insumo1.setFechaFabricacion(LocalDate.parse("2023-08-12"));
        insumo1.setFechaExpiracion(LocalDate.parse("2023-12-31"));
        insumo1.setEsCaducado(false);
        insumo1.setTipoInsumoIdPk(insumoDetalle);

        Insumo insumo2 = new Insumo();
        insumo2.setId(2L);
        insumo2.setCantidad(20);
        insumo2.setFechaFabricacion(LocalDate.parse("2023-06-15"));
        insumo2.setFechaExpiracion(LocalDate.parse("2023-11-30"));
        insumo2.setEsCaducado(true);
        insumo2.setTipoInsumoIdPk(insumoDetalle);

        insumoDetalle.setInsumoSet(new HashSet<>(Set.of(insumo1, insumo2)));

        InsumoDetalleDTO expectedDTO = new InsumoDetalleDTO(
                1L,
                "Insumo Detalle",
                "Descripción del insumo detalle",
                Set.of(
                        new InsumoDTO(1L, 50, LocalDate.parse("2023-08-12"), LocalDate.parse("2023-12-31"), false, 1L),
                        new InsumoDTO(2L, 20, LocalDate.parse("2023-06-15"), LocalDate.parse("2023-11-30"), true, 1L)
                )
        );

        InsumoDetalleDTO resultDTO = mapper.apply(insumoDetalle);

        assertEquals(expectedDTO, resultDTO);
    }
}
