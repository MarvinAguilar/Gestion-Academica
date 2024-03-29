package com.backend.controller;

import com.backend.model.GrupoModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "GrupoController", urlPatterns = {"/grupos", "/grupo", "/grupos-carrera", "/grupos-profesor"})
public class GrupoController extends HttpServlet {

    private static final GrupoModel model = GrupoModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/grupo":
                buscarGrupo(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/grupos":
                listarGrupo(request, response);
                break;
            case "/grupos-carrera":
                listarGrupoCarrera(request, response);
                break;
            case "/grupos-profesor":
                listarGrupoProfesor(request, response);
                break;
            default:
                request.setCharacterEncoding("UTF-8");
                JSONObject requestData = getJsonRequest(request);

                try {
                    model.insertarGrupo(
                            requestData.getInt("numero"),
                            requestData.getString("horario"),
                            requestData.getString("cedulaProfesor"),
                            requestData.getString("codigoCurso"),
                            requestData.getInt("annoCiclo"),
                            requestData.getInt("numeroCiclo")
                    );
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
                break;
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.modificarGrupo(
                    requestData.getInt("numero"),
                    requestData.getString("horario"),
                    requestData.getString("cedulaProfesor"),
                    requestData.getString("codigoCurso"),
                    requestData.getInt("annoCiclo"),
                    requestData.getInt("numeroCiclo")
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
            model.eliminarGrupo(requestData.getInt("numero"),
                    requestData.getString("codigoCurso"),
                    requestData.getInt("annoCiclo"),
                    requestData.getInt("numeroCiclo")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarGrupo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray grupos = new JSONArray();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            grupos = model.listarGrupo(requestData.optString("codigoCurso"), requestData.optInt("annoCiclo"), requestData.optInt("numeroCiclo"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(grupos));
    }

    protected void listarGrupoCarrera(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray grupos = new JSONArray();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            grupos = model.listarGrupoCarrera(requestData.optString("codigoCarrera"), requestData.optString("cedulaEstudiante"), requestData.optInt("annoCiclo"), requestData.optInt("numeroCiclo"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(grupos));
    }

    protected void listarGrupoProfesor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray grupos = new JSONArray();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            grupos = model.listarGrupoProfesor(requestData.optString("cedulaProfesor"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(grupos));
    }

    protected void buscarGrupo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject grupo = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            grupo = model.buscarGrupo(
                    requestData.getInt("numero"),
                    requestData.getString("cedulaProfesor"),
                    requestData.getString("codigoCurso"),
                    requestData.getInt("annoCiclo"),
                    requestData.getInt("numeroCiclo")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(grupo));
    }
}
