package com.backend.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Alumno implements Serializable {

    private String cedula;
    private String nombre;
    private String telefono;
    private String email;
    private LocalDate fechaNacimiento;
    private String carrera;

    public Alumno(String cedula, String nombre, String telefono, String email, LocalDate fechaNacimiento, String carrera) {
        this.setCedula(cedula);
        this.setNombre(nombre);
        this.setTelefono(telefono);
        this.setEmail(email);
        this.setFechaNacimiento(fechaNacimiento);
        this.setCarrera(carrera);
    }

    public Alumno() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", carrera=" + carrera +
                '}';
    }
}
