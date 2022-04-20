package com.backend.controller;

import com.backend.model.GrupoModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class GrupoController {

    private static GrupoModel model = GrupoModel.getInstance();
    private static GrupoController instance = null;

    public static GrupoController getInstance() {
        if (instance == null) instance = new GrupoController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarGrupo(object);
                case "GET_ALL":
                    return listarGrupo();
                case "POST":
                    return insertarGrupo(object);
                case "PUT":
                    return modificarGrupo(object);
                case "DELETE":
                    return eliminarGrupo(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarGrupo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject grupo = model.buscarGrupo(object.getInt("numero"),
                    object.getString("cedulaProfesor"),
                    object.getString("codigoCurso"),
                    object.getInt("annoCiclo"),
                    object.getInt("numeroCiclo"));
            response.put("request", "GET");
            response.put("Grupo", grupo);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarGrupo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarGrupo(object.getInt("numero"),
                    object.getString("horario"),
                    object.getString("cedulaProfesor"),
                    object.getString("codigoCurso"),
                    object.getInt("annoCiclo"),
                    object.getInt("numeroCiclo"));
            response.put("response", "CREATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarGrupo() {
        JSONObject response = new JSONObject();

        try {
            JSONArray Grupos = model.listarGrupo();
            response.put("request", "GET_ALL");
            response.put("Grupos", Grupos);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject modificarGrupo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.modificarGrupo(object.getInt("numero"),
                    object.getString("horario"),
                    object.getString("cedulaProfesor"),
                    object.getString("codigoCurso"),
                    object.getInt("annoCiclo"),
                    object.getInt("numeroCiclo"));
            response.put("response", "MODIFICATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject eliminarGrupo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarGrupo(object.getInt("numero"),
                    object.getString("codigoCurso"),
                    object.getInt("annoCiclo"),
                    object.getInt("numeroCiclo"));
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
