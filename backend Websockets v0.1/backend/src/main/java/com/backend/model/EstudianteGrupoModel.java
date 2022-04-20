package com.backend.model;

import com.backend.services.EstudianteGrupoDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class EstudianteGrupoModel {
    private static EstudianteGrupoModel instance = null;
    private final EstudianteGrupoDAO dao;

    public EstudianteGrupoModel() {
        this.dao = EstudianteGrupoDAO.getInstance();
    }

    public static EstudianteGrupoModel getInstance() {
        if (instance == null) instance = new EstudianteGrupoModel();
        return instance;
    }

    public JSONObject listarEstudiantesGrupo(int numeroGrupo, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        return this.dao.listarEstudiantesGrupo(numeroGrupo, codigoCurso, annoCiclo, numeroCiclo);
    }

    public JSONObject buscarGruposEstudiante(String cedulaEstudiante) throws SQLException {
        return this.dao.buscarGruposEstudiante(cedulaEstudiante);
    }

    public void matriculaEstudiante(String cedulaEstudiante, int numeroGrupo, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        this.dao.matriculaEstudiante(cedulaEstudiante, numeroGrupo, codigoCurso, annoCiclo, numeroCiclo);
    }

    public void ingresaNota(String cedulaEstudiante, int numeroGrupo, String codigoCurso, int annoCiclo, int numeroCiclo, Float nota) throws SQLException {
        this.dao.ingresaNota(cedulaEstudiante, numeroGrupo, codigoCurso, annoCiclo, numeroCiclo, nota);
    }

    public void desmatriculaEstudiante(String cedulaEstudiante, int numeroGrupo, String codigoCurso) throws SQLException {
        this.dao.desmatriculaEstudiante(cedulaEstudiante, numeroGrupo, codigoCurso);
    }
}
