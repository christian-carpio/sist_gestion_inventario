package com.uees.mgra.inventario.modals.dto;

import java.time.LocalDate;

public record InsumoDTO(Long id,
                        int cantidad,
                        LocalDate fechaFabricacion,
                        LocalDate fechaExpiracion,
                        boolean esCaducado,
                        Long insumoIdpk){
}
