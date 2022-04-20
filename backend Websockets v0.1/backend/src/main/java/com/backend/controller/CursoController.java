package com.backend.controller;

import com.backend.model.CursoModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class CursoController {
    private static CursoModel model = CursoModel.getInstance();
    private static CursoController instance = null;

    public static CursoController getInstance() {
        if (instance == null) instance = new CursoController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarCurso(object);
                case "GET_ALL":
                    return listarCurso();
                case "POST":
                    return insertarCurso(object);
                case "PUT":
                    return modificarCurso(object);
                case "DELETE":
                    return eliminarCurso(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarCurso(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject curso = model.buscarCurso(object.getString("codigo"));
            response.put("request", "GET");
            response.put("curso", curso);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarCurso() {
        JSONObject response = new JSONObject();

        try {
            JSONArray cursos = model.listarCurso();
            response.put("request", "GET_ALL");
            response.put("cursos", cursos);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarCurso(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarCurso(object.getString("codigo"),
                    object.getString("nombre"),
                    object.getInt("creditos"),
                    object.getInt("horasSemanales"));
            response.put("response", "CREATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject modificarCurso(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.modificarCurso(object.getString("codigo"),
                    object.getString("nombre"),
                    object.getInt("creditos"),
                    object.getInt("horasSemanales"));
            response.put("response", "MODIFICATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject eliminarCurso(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarCurso(object.getString("codigo"));
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
