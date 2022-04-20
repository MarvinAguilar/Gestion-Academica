package com.backend.controller;

import com.backend.model.CursoModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "CursoController", urlPatterns = {"/cursos", "/curso"})
public class CursoController extends HttpServlet {

    private static final CursoModel model = CursoModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/cursos":
                listarCurso(response);
                break;
            case "/curso":
                buscarCurso(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.insertarCurso(
                    requestData.getString("codigo"),
                    requestData.getString("nombre"),
                    requestData.getInt("creditos"),
                    requestData.getInt("horasSemanales")
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
            model.modificarCurso(
                    requestData.getString("codigo"),
                    requestData.getString("nombre"),
                    requestData.getInt("creditos"),
                    requestData.getInt("horasSemanales")
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
            model.eliminarCurso(requestData.optString("codigo"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarCurso(HttpServletResponse response) throws IOException {
        JSONArray cursos = new JSONArray();

        try {
            cursos = model.listarCurso();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(cursos));
    }

    protected void buscarCurso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject curso = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            curso = model.buscarCurso(requestData.optString("codigo"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(curso));
    }
}
