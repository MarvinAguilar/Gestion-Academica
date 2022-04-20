package com.backend.model;

import com.backend.services.UsuarioDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

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

    public JSONArray listarUsuario() throws SQLException {
        return this.dao.listarUsuario();
    }

    public JSONObject buscarUsuario(String cedula) throws SQLException {
        return this.dao.buscarUsuario(cedula);
    }

    public void insertarUsuario(String cedula, String nombre, int perfil) throws SQLException {
        this.dao.insertarUsuario(cedula, nombre, perfil);
    }

    public void modificarUsuario(String cedula, String nombre) throws SQLException {
        this.dao.modificarUsuario(cedula, nombre);
    }

    public void eliminarUsuario(String cedula) throws SQLException {
        this.dao.eliminarUsuario(cedula);
    }

    public String login(String cedula, String clave) throws SQLException {
        return this.dao.login(cedula, clave);
    }
}
