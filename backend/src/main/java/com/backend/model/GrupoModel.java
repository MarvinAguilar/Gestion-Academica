package com.backend.model;

import com.backend.services.GrupoDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class GrupoModel {
    private static GrupoModel instance = null;
    private final GrupoDAO dao;

    public GrupoModel() {
        this.dao = GrupoDAO.getInstance();
    }

    public static GrupoModel getInstance() {
        if (instance == null) {
            instance = new GrupoModel();
        }
        return instance;
    }

    public JSONArray listarGrupo(String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        return this.dao.listarGrupo(codigoCurso, annoCiclo, numeroCiclo);
    }

    public JSONArray listarGrupoCarrera(String codigoCarrera, int annoCiclo, int numeroCiclo) throws SQLException {
        return this.dao.listarGrupoCarrera(codigoCarrera, annoCiclo, numeroCiclo);
    }

    public JSONObject buscarGrupo(int numero, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        return this.dao.buscarGrupo(numero, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    }

    public void insertarGrupo(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        this.dao.insertarGrupo(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    }

    public void modificarGrupo(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        this.dao.modificarGrupo(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    }

    public void eliminarGrupo(int numero, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        this.dao.eliminarGrupo(numero, codigoCurso, annoCiclo, numeroCiclo);
    }
}
