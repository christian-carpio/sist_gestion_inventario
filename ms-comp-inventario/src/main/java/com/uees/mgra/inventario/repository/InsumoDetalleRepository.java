package com.uees.mgra.inventario.repository;

import com.uees.mgra.inventario.modals.entity.InsumoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsumoDetalleRepository extends JpaRepository<InsumoDetalle, Long> {
    boolean existsByNombreEqualsIgnoreCase(String nombre);

    Optional<InsumoDetalle> findByNombreEqualsIgnoreCase(String nombre);


}
