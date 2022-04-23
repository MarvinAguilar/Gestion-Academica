package com.backend.controller;

import com.backend.model.EstudianteGrupoModel;
import com.backend.model.UsuarioModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.SQLOutput;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "EstudianteGrupoController", urlPatterns = {"/estudiantes-grupo", "/grupos-estudiante"})
public class EstudianteGrupoController extends HttpServlet {

    private final static EstudianteGrupoModel model = EstudianteGrupoModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/estudiantes-grupo":
                listarEstudiantesGrupo(request, response);
                break;
//            case "/grupos-estudiante":
//                buscarGruposEstudiante(request, response);
//                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/grupos-estudiante".equals(request.getServletPath())) {
            buscarGruposEstudiante(request, response);
        } else {
            request.setCharacterEncoding("UTF-8");
            JSONObject requestData = getJsonRequest(request);

            try {
                model.matriculaEstudiante(
                        requestData.optString("cedulaEstudiante"),
                        requestData.optInt("numeroGrupo"),
                        requestData.optString("codigoCurso"),
                        requestData.optInt("annoCiclo"),
                        requestData.optInt("numeroCiclo")
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
            model.ingresaNota(
                    requestData.optString("cedulaEstudiante"),
                    requestData.optInt("numeroGrupo"),
                    requestData.optString("codigoCurso"),
                    requestData.optInt("annoCiclo"),
                    requestData.optInt("numeroCiclo"),
                    requestData.optFloat("nota")
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
            model.desmatriculaEstudiante(
                    requestData.optString("cedulaEstudiante"),
                    requestData.optInt("numeroGrupo"),
                    requestData.optString("codigoCurso")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarEstudiantesGrupo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject grupo = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            grupo = model.listarEstudiantesGrupo(
                    requestData.optInt("numeroGrupo"),
                    requestData.optString("codigoCurso"),
                    requestData.optInt("annoCiclo"),
                    requestData.optInt("numeroCiclo")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(grupo));
    }

    protected void buscarGruposEstudiante(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray gruposEstudiante = new JSONArray();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            gruposEstudiante = model.buscarGruposEstudiante(requestData.optString("cedulaEstudiante"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(gruposEstudiante));
    }
}
