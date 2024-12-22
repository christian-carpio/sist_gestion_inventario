package com.uees.mgra.producto.model.entity;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productoId;

    private String nombre;

    private BigDecimal precio;

    private String descripcion;

    private String userCreacion;

    @OneToMany(mappedBy = "productoIdFk", fetch = FetchType.EAGER)
    private Set<Requerimiento> requerimientoSet = new HashSet<>();

    public Producto() {
    }

    public Producto(Long productoId, String nombre, BigDecimal precio, String descripcion, String userCreacion, Set<Requerimiento> requerimientoSet) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.userCreacion = userCreacion;
        this.requerimientoSet = requerimientoSet;
    }

    public Long getProducto_id() {
        return productoId;
    }

    public void setProducto_id(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUserCreacion() {
        return userCreacion;
    }

    public void setUserCreacion(String userCreacion) {
        this.userCreacion = userCreacion;
    }

    public Set<Requerimiento> getRequerimientoSet() {
        return requerimientoSet;
    }

    public void setRequerimientoSet(Set<Requerimiento> requerimientoSet) {
        this.requerimientoSet = requerimientoSet;
    }
}
