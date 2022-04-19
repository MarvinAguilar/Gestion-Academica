package com.backend.controller;

import com.backend.model.AlumnoModel;
import com.backend.model.ProfesorModel;
import com.backend.model.UsuarioModel;
import org.javatuples.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.Session;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SessionController {
    private static final UsuarioModel model = UsuarioModel.getInstance();
    private static SessionController instance = null;


    public static SessionController getInstance() {
        if (instance == null) instance = new SessionController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object) {
        if (object == null) return null;

        try {
            switch (object.getString("request")) {
                case "LOGIN":
                    return login(object);
                case "LOGOUT":
                    return logout();
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject login(JSONObject object) {
        try {
            String cedula = object.getString("cedula");
            String clave = object.getString("clave");


            if (cedula == null && clave == null) return loginNoAutorizado();

            String autorizacion = model.login(cedula, clave);
            if (autorizacion == "None") return loginNoAutorizado();

            return loginAutorizado(cedula);
        } catch (SQLException e) {
            return null;
        }
    }

    public JSONObject logout() {


        JSONObject response = new JSONObject();
        response.put("request", "LOGOUT");
        return response;
    }

    public JSONObject loginAutorizado(String cedula) {
        JSONObject response = new JSONObject();


        try {
            JSONObject usuario = model.buscarUsuario(cedula);

            response.put("request", "LOGIN");
            response.put("cedula", cedula);
            response.put("perfil", usuario.getInt("perfil"));

            obtieneDatosUsuario(cedula, response, usuario.getInt("perfil"));


            return response;
        }catch(SQLException e){
            response.put("request", "ERROR");
            return response;
        }
    }

    public void obtieneDatosUsuario(String cedula, JSONObject response, int perfil){
        JSONObject usuario = null;
        try {
            switch (perfil) {
                case 3:
                    usuario = ProfesorModel.getInstance().buscarProfesor(cedula);
                    response.put("nombre", usuario.getString("nombre"));
                    response.put("telefono", usuario.getString("telefono"));
                    response.put("email", usuario.getString("email"));
                    break;
                case 4:
                    usuario = AlumnoModel.getInstance().buscarAlumno(cedula);
                    response.put("nombre", usuario.getString("nombre"));
                    response.put("telefono", usuario.getString("telefono"));
                    response.put("email", usuario.getString("email"));
                    response.put("fechaNacimiento", usuario.getString("fechaNacimiento"));
                    break;
            }
        }catch (SQLException e){

        }

    }

    public JSONObject loginNoAutorizado() {
        JSONObject response = new JSONObject();

        response.put("request", "ERROR");
        response.put("tipo", "credenciales");
        return response;
    }


}
