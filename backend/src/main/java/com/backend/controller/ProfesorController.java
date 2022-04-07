package com.backend.controller;

import com.backend.model.data.ProfesorDAO;
import com.backend.model.entity.Profesor;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet(name = "ProfesorController", urlPatterns = {"/profesor", "/profesor/{id}"})
public class ProfesorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/profesor":
                listarProfesor(response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void buscarProfesor(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ProfesorDAO profesorDAO = new ProfesorDAO();
//
//        System.out.println();
//
//        Profesor profesor = profesorDAO.buscarProfesor("116830152");
//
//        Gson gson = new Gson();
//        String profesorJSON = gson.toJson(profesor);
//
//        PrintWriter printWriter = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        printWriter.write(profesorJSON);
//
//        Enumeration<String> parameters = request.getParameterNames();
//
//        while (parameters.hasMoreElements()) {
//            String param = parameters.nextElement();
//            System.out.println(param);
//        }
//
//
//        printWriter.close();
    }

    private void listarProfesor(HttpServletResponse response) throws IOException {
//        ProfesorDAO profesorDAO = new ProfesorDAO();
//
//        ArrayList<Profesor> profesores = profesorDAO.listarProfesor();
//
//        Gson gson = new Gson();
//        String profesoresJSON = gson.toJson(profesores);
//
//        PrintWriter printWriter = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        printWriter.write(profesoresJSON);
//        printWriter.close();
    }
}
