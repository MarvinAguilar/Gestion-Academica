package com.backend.controller;

import com.backend.model.CarreraModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;


public class CarreraController {
    private static CarreraModel model = CarreraModel.getInstance();
    private static CarreraController instance = null;

    public static CarreraController getInstance() {
        if (instance == null) instance = new CarreraController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarCarrera(object);
                case "GET_ALL":
                    return listarCarrera();
                case "POST":
                    return insertarCarrera(object);
                case "PUT":
                    return modificarCarrera(object);
                case "DELETE":
                    return eliminarCarrera(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject carrera = model.buscarCarrera(object.getString("codigo"));
            response.put("request", "GET");
            response.put("carrera", carrera);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarCarrera() {
        JSONObject response = new JSONObject();

        try {
            JSONArray carreras = model.listarCarrera();
            response.put("request", "GET_ALL");
            response.put("carreras", carreras);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarCarrera(object.getString("codigo"),
                    object.getString("nombre"),
                    object.getString("titulo"));
            response.put("response", "CREATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject modificarCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.modificarCarrera(object.getString("codigo"),
                    object.getString("nombre"),
                    object.getString("titulo"));
            response.put("response", "MODIFICATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject eliminarCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarCarrera(object.getString("codigo"));
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
