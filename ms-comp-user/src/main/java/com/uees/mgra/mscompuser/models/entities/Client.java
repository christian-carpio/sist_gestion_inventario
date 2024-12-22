package com.uees.mgra.mscompuser.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User user;
    private String telefono;

    @Column(name = "puntosLealtad")
    private Long  puntosLealtad;
    private String urlImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getPuntosLealtad() {
        return puntosLealtad;
    }

    public void setPuntosLealtad(Long puntosLealtad) {
        this.puntosLealtad = puntosLealtad;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
