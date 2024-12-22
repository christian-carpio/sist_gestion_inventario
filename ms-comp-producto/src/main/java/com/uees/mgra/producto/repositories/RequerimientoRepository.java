package com.uees.mgra.producto.repositories;

import com.uees.mgra.producto.model.entity.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Long> {
}
