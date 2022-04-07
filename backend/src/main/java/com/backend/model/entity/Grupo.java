package com.backend.model.entity;

import java.io.Serializable;

public class Grupo implements Serializable {

    private int numero;
    private String horario;
    private String profesor;
    private String curso;
    private int numeroCiclo;
    private int annoCiclo;

    public Grupo(int numero, String horario, String profesor, String curso, int numeroCiclo, int annoCiclo) {
        this.setNumero(numero);
        this.setHorario(horario);
        this.setProfesor(profesor);
        this.setCurso(curso);
        this.setNumeroCiclo(numeroCiclo);
        this.setAnnoCiclo(annoCiclo);
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

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getNumeroCiclo() {
        return numeroCiclo;
    }

    public void setNumeroCiclo(int numeroCiclo) {
        this.numeroCiclo = numeroCiclo;
    }

    public int getAnnoCiclo() {
        return annoCiclo;
    }

    public void setAnnoCiclo(int annoCiclo) {
        this.annoCiclo = annoCiclo;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "numero=" + numero +
                ", horario='" + horario + '\'' +
                ", profesor='" + profesor + '\'' +
                ", curso='" + curso + '\'' +
                ", numeroCiclo=" + numeroCiclo +
                ", annoCiclo=" + annoCiclo +
                '}';
    }
}
