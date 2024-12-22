package com.uees.mgra.inventario.modals.dto;

import java.util.Set;

public record InsumoDetalleDTO(Long id, String nombre, String descripcion, Set<InsumoDTO> insumoDTOSet) {
}
