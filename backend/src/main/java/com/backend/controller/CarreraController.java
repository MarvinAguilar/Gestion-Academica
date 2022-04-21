package com.backend.controller;

import com.backend.model.CarreraModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "CarreraController", urlPatterns = {"/carreras", "/carrera"})
public class CarreraController extends HttpServlet {

    private static final CarreraModel model = CarreraModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/carreras":
                listarCarrera(response);
                break;
            case "/carrera":
                buscarCarrera(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            model.insertarCarrera(
                    requestData.getString("codigo"),
                    requestData.getString("nombre"),
                    requestData.getString("titulo")
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
            model.modificarCarrera(
                    requestData.getString("codigo"),
                    requestData.getString("nombre"),
                    requestData.getString("titulo")
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
            model.eliminarCarrera(requestData.optString("codigo"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarCarrera(HttpServletResponse response) throws IOException {
        JSONArray carreras = new JSONArray();

        try {
            carreras = model.listarCarrera();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(carreras));
    }

    protected void buscarCarrera(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject carrera = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            carrera = model.buscarCarrera(requestData.optString("codigo"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(carrera));
    }
}
