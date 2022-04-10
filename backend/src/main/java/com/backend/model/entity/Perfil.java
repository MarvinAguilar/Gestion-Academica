package com.backend.model.entity;

import java.io.Serializable;

public class Perfil implements Serializable {

    private int id;
    private String nombre;

    public Perfil(int id, String nombre) {
        this.setId(id);
        this.setNombre(nombre);
    }

    public Perfil() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
