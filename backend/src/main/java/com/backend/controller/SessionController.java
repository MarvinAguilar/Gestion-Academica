package com.backend.controller;

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

    private static final Map<String, Pair<String, String>> tokenData = new HashMap<>();
    private static Map<Session, String> sessionTokens = new HashMap<>();

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static SessionController getInstance() {
        if (instance == null) instance = new SessionController();
        return instance;
    }

    public JSONObject processRequest(JSONObject object, Session session) {
        if (object == null) return null;

        try {
            switch (object.getString("request")) {
                case "LOGIN":
                    return login(object.getString("cedula"), object.getString("clave"), session);
                case "LOGOUT":
                    return logout(session);
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject login(String cedula, String clave, Session session) {
        try {


            if (cedula == null && clave == null) return loginNoAutorizado();

            String autorizacion = model.login(cedula, clave);
            if (autorizacion == "None") return loginNoAutorizado();

            return loginAutorizado(cedula, clave, session);
        } catch (SQLException e) {
            return null;
        }
    }

    public JSONObject logout(Session session) {
        if (!sessionTokens.containsKey(session)) return null;

        String token = sessionTokens.get(session);
        sessionTokens = sessionTokens.entrySet().stream()
                .filter((e) -> !e.getValue().equals(token))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        tokenData.remove(token);

        JSONObject response = new JSONObject();
        response.put("request", "LOGOUT");
        return response;
    }

    public JSONObject loginAutorizado(String cedula, String clave, Session session) {
        JSONObject response = new JSONObject();

        String token = generarToken();
        sessionTokens.put(session, token);
        tokenData.put(token, Pair.with(cedula, clave));

        response.put("request", "LOGIN");
        response.put("cedula", cedula);
        response.put("token", token);
        return response;
    }

    public JSONObject loginNoAutorizado() {
        JSONObject response = new JSONObject();

        response.put("request", "ERROR");
        response.put("tipo", "credenciales");
        return response;
    }

    private Pair<String, String> obtenerDatosSession(Session session) {
        String token = sessionTokens.get(session);
        return tokenData.get(token);
    }

    private JSONObject loginActivo(Session session) {
        JSONObject response = new JSONObject();

        String token = sessionTokens.get(session);
        Object cedula = obtenerDatosSession(session).getValue(0);

        response.put("request", "LOGIN");
        response.put("cedula", cedula);
        response.put("token", token);
        return response;
    }


    private static String generarToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
