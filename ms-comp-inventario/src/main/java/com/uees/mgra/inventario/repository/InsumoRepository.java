package com.uees.mgra.inventario.repository;

import com.uees.mgra.inventario.modals.entity.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {

    @Query("SELECT i FROM Insumo i WHERE i.esCaducado = false AND (i.fechaExpiracion <= :fechaExpirado)")
    List<Insumo> findCaducado(@Param("fechaExpirado") LocalDate fechaExpiracion);

    @Query("SELECT i FROM Insumo i WHERE i.insumoDetalleIdPk.id = :id")
    List<Insumo> findPersonalizada(@Param("id") Long id);
}
