package com.uees.mgra.inventario.modals.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int cantidad;

    @Column(name = "fechaFabricacion")
    private LocalDate fechaFabricacion;

    @Column(name = "fechaExpiracion")
    private LocalDate fechaExpiracion;

    private boolean esCaducado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insumoDetalleIdPk", nullable = false)
    private InsumoDetalle insumoDetalleIdPk;

    public Insumo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public boolean isEsCaducado() {
        return esCaducado;
    }

    public void setEsCaducado(boolean esCaducado) {
        this.esCaducado = esCaducado;
    }

    public InsumoDetalle getTipoInsumoIdPk() {
        return insumoDetalleIdPk;
    }

    public void setTipoInsumoIdPk(InsumoDetalle insumoDetalleIdPk) {
        this.insumoDetalleIdPk = insumoDetalleIdPk;
    }
}
