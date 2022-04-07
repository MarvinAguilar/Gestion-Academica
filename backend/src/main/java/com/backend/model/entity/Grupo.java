package com.backend.model.entity;

import java.io.Serializable;

public class Grupo implements Serializable {

    private int numero;
    private String horario;
    private Profesor profesor;
    private Curso curso;
    private Ciclo ciclo;

    public Grupo(int numero, String horario, Profesor profesor, Curso curso, Ciclo ciclo) {
        this.setNumero(numero);
        this.setHorario(horario);
        this.setProfesor(profesor);
        this.setCurso(curso);
        this.setCiclo(ciclo);
    }

    public Grupo() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "numero=" + numero +
                ", horario='" + horario + '\'' +
                ", profesor=" + profesor +
                ", curso=" + curso +
                ", ciclo=" + ciclo +
                '}';
    }
}
