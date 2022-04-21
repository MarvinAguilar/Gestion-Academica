package com.backend.controller;

import com.backend.model.AlumnoModel;
import com.backend.model.ProfesorModel;
import com.backend.model.UsuarioModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuarios", "/usuario", "/login"})
public class UsuarioController extends HttpServlet {

    private final static UsuarioModel model = UsuarioModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/usuarios":
                listarUsuario(response);
                break;
            case "/usuario":
                buscarUsuario(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/login".equals(request.getServletPath())) {
            login(request, response);
        } else {
            request.setCharacterEncoding("UTF-8");
            JSONObject requestData = getJsonRequest(request);

            try {
                model.insertarUsuario(
                        requestData.getString("cedula"),
                        requestData.getString("clave"),
                        requestData.getInt("perfil")
                );
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.modificarUsuario(
                    requestData.getString("cedula"),
                    requestData.getString("clave")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.eliminarUsuario(requestData.optString("cedula"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarUsuario(HttpServletResponse response) throws IOException {
        JSONArray usuarios = new JSONArray();

        try {
            usuarios = model.listarUsuario();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(usuarios));
    }

    protected void buscarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject usuario = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            usuario = model.buscarUsuario(requestData.optString("cedula"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(usuario));
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject usuario = new JSONObject();
        JSONObject usuarioEncontrado = null;

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            String autorizado = model.login(requestData.optString("cedula"), requestData.optString("clave"));

            if (!Objects.equals(autorizado, "None")) {
                usuarioEncontrado = model.buscarUsuario(requestData.optString("cedula"));
                int perfil = usuarioEncontrado.optInt("perfil");

                switch (perfil) {
                    case 3:
                        usuario = ProfesorModel.getInstance().buscarProfesor(requestData.optString("cedula"));
                        usuario.put("perfil", perfil);
                        break;
                    case 4:
                        usuario = AlumnoModel.getInstance().buscarAlumno(requestData.optString("cedula"));
                        usuario.put("perfil", perfil);
                        break;
                    default:
                        usuario.put("cedula", requestData.optString("cedula"));
                        usuario.put("perfil", perfil);
                        break;
                }
            } else {
                usuario.put("cedula", "None");
            }
        } catch (SQLException e) {
            usuario.put("cedula", "None");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(usuario));
    }
}
