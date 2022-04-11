package com.backend.model.entity;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String nombre;
    private String cedula;
    private int perfil;

    public Usuario(String cedula, String nombre,  int perfil) {
        this.setNombre(nombre);
        this.setCedula(cedula);
        this.setPerfil(perfil);
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", perfil=" + perfil +
                '}';
    }
}
