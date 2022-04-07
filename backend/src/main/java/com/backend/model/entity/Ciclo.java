package com.backend.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Ciclo implements Serializable {

    private int anno;
    private int numero;
    private LocalDate fechaInicio;
    private LocalDate fechafinal;
    private int estado;

    public Ciclo(int anno, int numero, LocalDate fechaInicio, LocalDate fechafinal, int estado) {
        this.setAnno(anno);
        this.setNumero(numero);
        this.setFechaInicio(fechaInicio);
        this.setFechafinal(fechafinal);
        this.setEstado(estado);
    }

    public Ciclo() {
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(LocalDate fechafinal) {
        this.fechafinal = fechafinal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ciclo{" +
                "anno=" + anno +
                ", numero=" + numero +
                ", fechaInicio=" + fechaInicio +
                ", fechafinal=" + fechafinal +
                ", estado=" + estado +
                '}';
    }
}
