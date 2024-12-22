package com.uees.mgra.inventario.modals.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class InsumoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String descripcion;
    @OneToMany(mappedBy = "insumoDetalleIdPk", fetch = FetchType.EAGER)
    private Set<Insumo> insumoSet = new HashSet<>();

    public InsumoDetalle() {
    }



    public InsumoDetalle(Long id, String nombre, String descripcion, Set<Insumo> insumoSet) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.insumoSet = insumoSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Insumo> getInsumoSet() {
        return insumoSet;
    }

    public void setInsumoSet(Set<Insumo> insumoSet) {
        this.insumoSet = insumoSet;
    }

    @Override
    public String toString() {
        return "TipoInsumo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", insumoSet=" + insumoSet +
                '}';
    }
}
