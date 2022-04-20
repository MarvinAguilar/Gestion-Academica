package com.backend.controller;

import com.backend.model.CicloModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.backend.utils.requestToJson.getJsonRequest;

@WebServlet(name = "CicloController", urlPatterns = {"/ciclos", "/ciclo"})
public class CicloController extends HttpServlet {

    private final static CicloModel model = CicloModel.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/ciclos":
                listarCiclo(response);
                break;
            case "/ciclo":
                buscarCiclo(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);
        try {
            model.insertarCiclo(
                    requestData.optInt("anno"),
                    requestData.optInt("numero"),
                    requestData.optString("fechaInicio"),
                    requestData.optString("fechaFinal"),
                    requestData.optInt("estado")
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
            model.modificarCiclo(
                    requestData.getInt("anno"),
                    requestData.getInt("numero"),
                    requestData.getString("fechaInicio"),
                    requestData.getString("fechaFinal"),
                    requestData.getInt("estado")
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
            model.eliminarCiclo(requestData.optInt("anno"), requestData.optInt("numero"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void listarCiclo(HttpServletResponse response) throws IOException {
        JSONArray ciclos = new JSONArray();

        try {
            ciclos = model.listarCiclo();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(ciclos));
    }

    protected void buscarCiclo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject ciclo = new JSONObject();

        request.setCharacterEncoding("UTF-8");
        JSONObject requestData = getJsonRequest(request);

        try {
            ciclo = model.buscarCiclo(requestData.optInt("anno"), requestData.optInt("numero"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(ciclo));
    }

}
