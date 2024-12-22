package com.uees.mgra.producto.model.dto;

import com.uees.mgra.producto.model.entity.Requerimiento;

import java.math.BigDecimal;
import java.util.Set;

public record ProductoDTO(Long id, String nombre, BigDecimal precio, String descripcion, String nombreCreador, Set<RequerimientoDTO> requerimientoDTOSet) {
}


