package com.backend.model;

import com.backend.services.CarreraDAO;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public JSONArray listarCarrera() {
        return this.dao.listarCarrera();
    }

    public JSONObject buscarCarrera(String codigo) {
        return this.dao.buscarCarrera(codigo);
    }

    public void insertarCarrera(String codigo, String nombre, String titulo) {
        this.dao.insertarCarrera(codigo, nombre, titulo);
    }

    public void modificarCarrera(String codigo, String nombre, String titulo) {
        this.dao.modificarCarrera(codigo, nombre, titulo);
    }

    public void eliminarCarrera(String codigo) {
        this.dao.eliminarCarrera(codigo);
    }
}
