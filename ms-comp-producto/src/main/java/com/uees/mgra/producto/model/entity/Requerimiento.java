package com.uees.mgra.producto.model.entity;

import jakarta.persistence.*;

@Table
@Entity
public class Requerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoIdFk", nullable = false)
    private Producto productoIdFk;

    @Column(name = "tipoinsumoid")
    private Long tipoInsumoIdFK;


    public Requerimiento() {
    }

    public Requerimiento(Long id, Producto productoIdFk, Long tipoInsumoIdFK, Long cantidad) {
        this.id = id;
        this.productoIdFk = productoIdFk;
        this.tipoInsumoIdFK = tipoInsumoIdFK;
        this.cantidad = cantidad;
    }

    private Long cantidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto_id() {
        return productoIdFk;
    }

    public void setProducto_id(Producto productoIdFk) {
        this.productoIdFk = productoIdFk;
    }

    public Long getTipoInsumoIdFK() {
        return tipoInsumoIdFK;
    }

    public void setTipoInsumoIdFK(Long tipoInsumoIdFK) {
        this.tipoInsumoIdFK = tipoInsumoIdFK;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
