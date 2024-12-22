package com.uees.mgra.producto.util.response;


import java.io.Serializable;

public class ResponseInventario implements Serializable {
    public Long id;
    public Long cantidad;
    public String fechaFabricacion;
    public String fechaExpiracion;
    public Boolean esCaducado;
    public Insumo insumo;

    public static class Insumo implements Serializable {
        public Long id;
        public String nombre;
        public String descripcion;
    }

}


