package com.backend.controller;

import com.backend.model.EstudianteGrupoModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class EstudianteGrupoController {
    private static EstudianteGrupoModel model = EstudianteGrupoModel.getInstance();
    private static EstudianteGrupoController instance = null;

    public static EstudianteGrupoController getInstance() {
        if (instance == null) instance = new EstudianteGrupoController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarGruposEstudiante(object);
                case "GET_ALL":
                    return listarEstudiantesGrupo(object);
                case "POST":
                    return matriculaEstudiante(object);
                case "PUT":
                    return ingresaNota(object);
                case "DELETE":
                    return desmatriculaEstudiante(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarGruposEstudiante(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject grupos = model.buscarGruposEstudiante(object.getString("cedulaEstudiante"));
            response.put("request", "GET");
            response.put("grupos", grupos);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarEstudiantesGrupo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject estudiantes = model.listarEstudiantesGrupo(object.getInt("numeroGrupo"),
                    object.getString("codigoCurso"),
                    object.getInt("annoCiclo"),
                    object.getInt("numeroCiclo"));
            response.put("request", "GET_ALL");
            response.put("estudiantes", estudiantes);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject matriculaEstudiante(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.matriculaEstudiante(object.getString("cedulaEstudiante"),
                    object.getInt("numeroGrupo"),
                    object.getString("codigoCurso"),
                    object.getInt("annoCiclo"),
                    object.getInt("numeroCiclo"));
            response.put("response", "ENROLLED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject ingresaNota(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.ingresaNota(object.getString("cedulaEstudiante"),
                    object.getInt("numeroGrupo"),
                    object.getString("codigoCurso"),
                    object.getInt("annoCiclo"),
                    object.getInt("numeroCiclo"),
                    object.getFloat("nota"));
            response.put("response", "QUALIFIED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject desmatriculaEstudiante(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.desmatriculaEstudiante(object.getString("cedulaEstudiante"),
                    object.getInt("annoCiclo"),
                    object.getString("cedulaEstudiante"));
            response.put("response", "WITHDRAWN");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }
}
