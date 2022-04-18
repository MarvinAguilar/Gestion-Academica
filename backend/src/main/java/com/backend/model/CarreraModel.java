package com.backend.model;

import com.backend.services.CarreraDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class CarreraModel {
    private static CarreraModel instance = null;
    private final CarreraDAO dao;

    public CarreraModel() {
        this.dao = CarreraDAO.getInstance();
    }

    public static CarreraModel getInstance() {
        if (instance == null) instance = new CarreraModel();
        return instance;
    }

    public JSONArray listarCarrera() throws SQLException {
        return this.dao.listarCarrera();
    }

    public JSONObject buscarCarrera(String codigo) throws SQLException {
        return this.dao.buscarCarrera(codigo);
    }

    public void insertarCarrera(String codigo, String nombre, String titulo) throws SQLException {
        this.dao.insertarCarrera(codigo, nombre, titulo);
    }

    public void modificarCarrera(String codigo, String nombre, String titulo) throws SQLException {
        this.dao.modificarCarrera(codigo, nombre, titulo);
    }

    public void eliminarCarrera(String codigo) throws SQLException {
        this.dao.eliminarCarrera(codigo);
    }
}
