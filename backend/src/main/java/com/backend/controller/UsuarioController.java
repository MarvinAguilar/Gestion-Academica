package com.backend.controller;

import com.backend.model.UsuarioModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class UsuarioController {

    private static UsuarioModel model = UsuarioModel.getInstance();
    private static UsuarioController instance = null;

    public static UsuarioController getInstance() {
        if (instance == null) instance = new UsuarioController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarUsuario(object);
                case "GET_ALL":
                    return listarUsuario();
                case "POST":
                    return insertarUsuario(object);
                case "PUT":
                    return modificarUsuario(object);
                case "DELETE":
                    return eliminarUsuario(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarUsuario(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject usuario = model.buscarUsuario(object.getString("cedula"));
            response.put("request", "GET");
            response.put("usuario", usuario);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarUsuario() {
        JSONObject response = new JSONObject();

        try {
            JSONArray usuarios = model.listarUsuario();
            response.put("request", "GET_ALL");
            response.put("usuarios", usuarios);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarUsuario(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarUsuario(object.getString("cedula"),
                    object.getString("clave"),
                    object.getInt("perfil"));
            response.put("response", "CREATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject modificarUsuario(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.modificarUsuario(object.getString("cedula"),
                    object.getString("clave"),
                    object.getInt("perfil"));
            response.put("response", "MODIFICATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject eliminarUsuario(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarUsuario(object.getString("cedula"));
            response.put("response", "ELIMINATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }
    
}
