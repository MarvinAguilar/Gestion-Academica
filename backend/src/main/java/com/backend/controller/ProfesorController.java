package com.backend.controller;

import com.backend.model.ProfesorModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "ProfesorController", urlPatterns = {"/profesores", "/profesor"})
public class ProfesorController extends HttpServlet {

    private static final ProfesorModel model = ProfesorModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/profesores":
                listarProfesor(response);
                break;
            case "/profesor":
                buscarProfesor(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.insertarProfesor(
                    requestData.optString("cedula"),
                    requestData.optString("nombre"),
                    requestData.optString("telefono"),
                    requestData.optString("email")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.modificarProfesor(
                    requestData.optString("cedula"),
                    requestData.optString("nombre"),
                    requestData.optString("telefono"),
                    requestData.optString("email")
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
            model.eliminarProfesor(requestData.optString("cedula"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarProfesor(HttpServletResponse response) throws IOException {
        JSONArray profesor = new JSONArray();

        try {
            profesor = model.listarProfesor();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(profesor));
    }

    protected void buscarProfesor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject carrera = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            carrera = model.buscarProfesor(requestData.optString("cedula"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(carrera));
    }

}
