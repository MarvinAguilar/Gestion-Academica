package com.backend.model;

import com.backend.services.AlumnoDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class AlumnoModel {
    private static AlumnoModel instance = null;
    private final AlumnoDAO dao;

    public AlumnoModel() {
        this.dao = AlumnoDAO.getInstance();
    }

    public static AlumnoModel getInstance() {
        if (instance == null) instance = new AlumnoModel();
        return instance;
    }

    public JSONArray listarAlumno() throws SQLException {
        return this.dao.listarAlumno();
    }

    public JSONObject buscarAlumno(String cedula) throws SQLException {
        return this.dao.buscarAlumno(cedula);
    }

    public void insertarAlumno(String cedula, String nombre, String telefono, String email, String fechaNacimiento, String carrera) throws SQLException {
        this.dao.insertarAlumno(cedula, nombre, telefono, email, fechaNacimiento, carrera);
    }

    public void modificarAlumno(String cedula, String nombre, String telefono, String email, String fechaNacimiento, String carrera) throws SQLException {
        this.dao.modificarAlumno(cedula, nombre, telefono, email, fechaNacimiento, carrera);
    }

    public void eliminarAlumno(String cedula) throws SQLException {
        this.dao.eliminarAlumno(cedula);
    }
}
