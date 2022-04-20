package com.backend.controller;

import com.backend.model.CursosCarreraModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "CursosCarreraController", urlPatterns = {"/curso-carreras", "/curso-carrera"})
public class CursosCarreraController extends HttpServlet {

    private final static CursosCarreraModel model = CursosCarreraModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/curso-carreras":
                listarCurso(request, response);
                break;
            case "/curso-carrera":
                buscarCursos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.insertarCursosCarrera(
                    requestData.optString("codigoCarrera"),
                    requestData.optString("codigoCurso"),
                    requestData.optInt("annoCiclo"),
                    requestData.optInt("numeroCiclo")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.modificarCursosCarrera(
                    requestData.optString("codigoCarrera"),
                    requestData.optString("codigoCurso"),
                    requestData.optInt("annoCiclo"),
                    requestData.optInt("numeroCiclo")
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
            model.eliminarCursosCarrera(requestData.optString("codigoCarrera"),
                    requestData.optString("codigoCurso"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject cursos = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);
        try {
            cursos = model.listarCursosCarrera(requestData.optString("codigoCarrera"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(cursos));
    }

    protected void buscarCursos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject curso = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            curso = model.buscarCursosCarrera(requestData.optString("codigoCarrera"), requestData.optString("codigoCurso"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(curso));
    }

}
