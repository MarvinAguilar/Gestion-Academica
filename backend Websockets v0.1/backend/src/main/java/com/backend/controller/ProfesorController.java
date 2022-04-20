package com.backend.controller;

import com.backend.model.CursoModel;
import com.backend.model.ProfesorModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class ProfesorController {
    private static ProfesorModel model = ProfesorModel.getInstance();
    private static ProfesorController instance = null;

    public static ProfesorController getInstance() {
        if (instance == null) instance = new ProfesorController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarProfesor(object);
                case "GET_ALL":
                    return listarProfesor();
                case "POST":
                    return insertarProfesor(object);
                case "PUT":
                    return modificarProfesor(object);
                case "DELETE":
                    return eliminarProfesor(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarProfesor(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject profesor = model.buscarProfesor(object.getString("cedula"));
            response.put("request", "GET");
            response.put("profesor", profesor);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarProfesor() {
        JSONObject response = new JSONObject();

        try {
            JSONArray profesores = model.listarProfesor();
            response.put("request", "GET_ALL");
            response.put("profesores", profesores);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarProfesor(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarProfesor(object.getString("cedula"),
                    object.getString("nombre"),
                    object.getString("telefono"),
                    object.getString("email"));
            response.put("response", "CREATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject modificarProfesor(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.modificarProfesor(object.getString("cedula"),
                    object.getString("nombre"),
                    object.getString("telefono"),
                    object.getString("email"));
            response.put("response", "MODIFICATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject eliminarProfesor(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarProfesor(object.getString("cedula"));
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
