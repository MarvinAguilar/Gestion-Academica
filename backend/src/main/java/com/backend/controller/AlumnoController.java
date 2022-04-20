package com.backend.controller;

import com.backend.model.AlumnoModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "AlumnoController", urlPatterns = {"/alumnos", "/alumno"})
public class AlumnoController extends HttpServlet {

    private final static AlumnoModel model = AlumnoModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/alumnos":
                listarAlumno(response);
                break;
            case "/alumno":
                buscarAlumno(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);
        try {
            model.insertarAlumno(
                    requestData.getString("cedula"),
                    requestData.getString("nombre"),
                    requestData.getString("telefono"),
                    requestData.getString("email"),
                    requestData.getString("fechaNacimiento"),
                    requestData.getString("carrera")
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
            model.modificarAlumno(
                    requestData.getString("cedula"),
                    requestData.getString("nombre"),
                    requestData.getString("telefono"),
                    requestData.getString("email"),
                    requestData.getString("fechaNacimiento"),
                    requestData.getString("carrera")
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
            model.eliminarAlumno(
                    requestData.getString("cedula")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarAlumno(HttpServletResponse response) throws IOException {
        JSONArray alumnos = new JSONArray();

        try {
            alumnos = model.listarAlumno();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(alumnos));
    }

    protected void buscarAlumno(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject alumno = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            alumno = model.buscarAlumno(requestData.getString("cedula"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(alumno));
    }
}
