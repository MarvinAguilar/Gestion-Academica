package com.backend.controller;

import com.backend.model.CicloModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class CicloController {

    private static CicloModel model = CicloModel.getInstance();
    private static CicloController instance = null;

    public static CicloController getInstance() {
        if (instance == null) instance = new CicloController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarCiclo(object);
                case "GET_ALL":
                    return listarCiclo();
                case "POST":
                    return insertarCiclo(object);
                case "PUT":
                    return modificarCiclo(object);
                case "DELETE":
                    return eliminarCiclo(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarCiclo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject ciclo = model.buscarCiclo(object.getInt("anno"), object.getInt("numero"));
            response.put("request", "GET");
            response.put("ciclo", ciclo);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarCiclo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarCiclo(object.getInt("anno"),
                    object.getInt("numero"),
                    object.getString("fechaInicio"),
                    object.getString("fechaFinal"),
                    object.getInt("estado"));
            response.put("response", "CREATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject listarCiclo() {
        JSONObject response = new JSONObject();

        try {
            JSONArray ciclos = model.listarCiclo();
            response.put("request", "GET_ALL");
            response.put("Ciclos", ciclos);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject modificarCiclo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.modificarCiclo(object.getInt("anno"),
                    object.getInt("numero"),
                    object.getString("fechaInicio"),
                    object.getString("fechaFinal"),
                    object.getInt("estado"));
            response.put("response", "MODIFICATED");
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject eliminarCiclo(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarCiclo(object.getInt("anno"), object.getInt("numero"));
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
