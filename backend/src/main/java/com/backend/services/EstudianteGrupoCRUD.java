package com.backend.services;

public class EstudianteGrupoCRUD {

    protected static final String MATRICULAESTUDIANTE = "{call spMatriculaEstudiante(?, ?, ?, ?, ?, ?)}";
    protected static final String INGRESANOTA = "{call spIngresaNota(?, ?, ?, ?)}";
    protected static final String DESMATRICULAESTUDIANTE = "{call spDesmatriculaEstudiante(?, ?, ?)}";
    protected static final String BUSCARGRUPOSESTUDIANTE = "{?=call buscarGruposEstudiante(?)}";
    protected static final String LISTARESTUDIANTESGRUPO = "{?=call listarEstudiantesGrupo(?, ?)}";

}
