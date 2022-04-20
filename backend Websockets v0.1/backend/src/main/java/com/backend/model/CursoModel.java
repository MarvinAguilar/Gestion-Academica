package com.backend.model;


import com.backend.services.CursoDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class CursoModel {
    private static CursoModel instance = null;
    private final CursoDAO dao;

    public CursoModel() {
        this.dao = CursoDAO.getInstance();
    }

    public static CursoModel getInstance() {
        if (instance == null) instance = new CursoModel();
        return instance;
    }

    public JSONArray listarCurso() throws SQLException {
        return this.dao.listarCurso();
    }

    public JSONObject buscarCurso(String codigo) throws SQLException {
        return this.dao.buscarCurso(codigo);
    }

    public void insertarCurso(String codigo, String nombre, int creditos, int horasSemanales) throws SQLException {
        this.dao.insertarCurso(codigo, nombre, creditos, horasSemanales);
    }

    public void modificarCurso(String codigo, String nombre, int creditos, int horasSemanales) throws SQLException {
        this.dao.modificarCurso(codigo, nombre, creditos, horasSemanales);
    }

    public void eliminarCurso(String codigo) throws SQLException {
        this.dao.eliminarCurso(codigo);
    }
}
