package com.backend.model;

import com.backend.services.UsuarioDAO;
import org.json.JSONArray;
import org.json.JSONObject;

public class UsuarioModel {
    private static UsuarioModel instance = null;
    private final UsuarioDAO dao;

    public UsuarioModel() {
        this.dao = UsuarioDAO.getInstance();
    }

    public static UsuarioModel getInstance() {
        if (instance == null) instance = new UsuarioModel();
        return instance;
    }

    public JSONArray listarUsuario() {
        return this.dao.listarUsuario();
    }

    public JSONObject buscarUsuario(String cedula) {
        return this.dao.buscarUsuario(cedula);
    }

    public void insertarUsuario(String cedula, String nombre, int perfil) {
        this.dao.insertarUsuario(cedula, nombre, perfil);
    }

    public void modificarUsuario(String cedula, String nombre, int perfil) {
        this.dao.modificarUsuario(cedula, nombre, perfil);
    }

    public void eliminarUsuario(String cedula) {
        this.dao.eliminarUsuario(cedula);
    }
}
