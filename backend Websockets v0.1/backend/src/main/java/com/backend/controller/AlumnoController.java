package com.backend.controller;

import com.backend.model.AlumnoModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class AlumnoController {

    private static AlumnoModel model = AlumnoModel.getInstance();
    private static AlumnoController instance = null;

    public static AlumnoController getInstance() {
        if (instance == null) instance = new AlumnoController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarAlumno(object);
                case "GET_ALL":
                    return listarAlumno();
                case "POST":
                    return insertarAlumno(object);
                case "PUT":
                    return modificarAlumno(object);
                case "DELETE":
                    return eliminarAlumno(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarAlumno(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject alumno = model.buscarAlumno(object.getString("cedula"));
            response.put("request", "GET");
            response.put("alumno", alumno);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarAlumno(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarAlumno(object.getString("cedula"),
                    object.getString("nombre"),
                    object.getString("telefono"),
                    object.getString("email"),
                    object.getString("fechaNacimiento"),
                    object.getString("Alumno"));
            response.put("response", "CREATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarAlumno() {
        JSONObject response = new JSONObject();

        try {
            JSONArray Alumnos = model.listarAlumno();
            response.put("request", "GET_ALL");
            response.put("Alumnos", Alumnos);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject modificarAlumno(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.modificarAlumno(object.getString("cedula"),
                    object.getString("nombre"),
                    object.getString("telefono"),
                    object.getString("email"),
                    object.getString("fechaNacimiento"),
                    object.getString("Alumno"));
            response.put("response", "MODIFICATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject eliminarAlumno(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarAlumno(object.getString("cedula"));
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
