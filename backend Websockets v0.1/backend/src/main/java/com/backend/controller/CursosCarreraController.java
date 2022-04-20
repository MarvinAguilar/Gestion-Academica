package com.backend.controller;

import com.backend.model.CursosCarreraModel;
import com.backend.model.CursoModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class CursosCarreraController {

    private static CursosCarreraModel model = CursosCarreraModel.getInstance();
    private static CursosCarreraController instance = null;

    public static CursosCarreraController getInstance() {
        if (instance == null) instance = new CursosCarreraController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;
        try {
            switch (object.getString("request")) {
                case "GET":
                    return buscarCursosCarrera(object);
                case "GET_ALL":
                    return listarCursosCarrera(object);
                case "POST":
                    return insertarCursosCarrera(object);
                case "PUT":
                    return null;
                case "DELETE":
                    return eliminarCursosCarrera(object);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject buscarCursosCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONObject cursosCarrera = model.buscarCursosCarrera(object.getString("codigoCarrera"), object.getString("codigoCurso"));
            response.put("request", "GET");
            response.put("CursosCarrera", cursosCarrera);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

    public JSONObject insertarCursosCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.insertarCursosCarrera(object.getString("codigoCarrera"),
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

    public JSONObject listarCursosCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            JSONArray cursosCarreras = model.listarCursosCarrera(object.getString("codigoCarrera"));
            response.put("request", "GET_ALL");
            response.put("CursosCarreras", cursosCarreras);
        } catch (SQLException e) {
            response.put("response", "ERROR");
            response.put("message", e.getMessage());
        } catch (JSONException e) {
            return null;
        }

        return response;
    }

//    public JSONObject modificarCursosCarrera(JSONObject object) {
//        JSONObject response = new JSONObject();
//
//        try {
//            model.modificarCursosCarrera(object.getInt("anno"),
//                    object.getInt("numero"),
//                    object.getString("fechaInicio"),
//                    object.getString("fechaFinal"),
//                    object.getInt("estado"));
//            response.put("response", "MODIFICATED");
//        } catch (SQLException e) {
//            response.put("response", "ERROR");
//            response.put("message", e.getMessage());
//        } catch (JSONException e) {
//            return null;
//        }
//
//        return response;
//    }

    public JSONObject eliminarCursosCarrera(JSONObject object) {
        JSONObject response = new JSONObject();

        try {
            model.eliminarCursosCarrera(object.getString("codigoCarrera"), object.getString("codigoCurso"));
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
