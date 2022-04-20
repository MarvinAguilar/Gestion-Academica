package com.backend.model;

import com.backend.services.ProfesorDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class ProfesorModel {
    private static ProfesorModel instance = null;
    private final ProfesorDAO dao;

    public ProfesorModel() {
        this.dao = ProfesorDAO.getInstance();
    }

    public static ProfesorModel getInstance() {
        if (instance == null) instance = new ProfesorModel();
        return instance;
    }

    public JSONArray listarProfesor() throws SQLException {
        return this.dao.listarProfesor();
    }

    public JSONObject buscarProfesor(String cedula) throws SQLException {
        return this.dao.buscarProfesor(cedula);
    }

    public void insertarProfesor(String cedula, String nombre, String telefono, String email) throws SQLException {
        this.dao.insertarProfesor(cedula, nombre, telefono, email);
    }

    public void modificarProfesor(String cedula, String nombre, String telefono, String email) throws SQLException {
        this.dao.modificarProfesor(cedula, nombre, telefono, email);
    }

    public void eliminarProfesor(String cedula) throws SQLException {
        this.dao.eliminarProfesor(cedula);
    }
}
