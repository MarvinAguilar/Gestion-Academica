package com.backend.model;

import com.backend.services.CursosCarreraDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class CursosCarreraModel {
    private static CursosCarreraModel instance = null;
    private final CursosCarreraDAO dao;

    public CursosCarreraModel() {
        this.dao = CursosCarreraDAO.getInstance();
    }

    public static CursosCarreraModel getInstance() {
        if (instance == null) instance = new CursosCarreraModel();
        return instance;
    }

    public JSONArray listarCursosCarrera(String carrera) throws SQLException {
        return this.dao.listarCurso(carrera);
    }

    public JSONObject buscarCursosCarrera(String carrera, String codigoCurso) throws SQLException {
        return this.dao.buscarCurso(carrera, codigoCurso);
    }

    public void insertarCursosCarrera(String carrera, String curso, int anno, int numeroCiclo) throws SQLException {
        this.dao.insertarCursoCarrera(carrera, curso, anno, numeroCiclo);
    }

    //public void modificarCursosCarrera(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo){
    //    this.dao.modificarCursosCarrera(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    //}

    public void eliminarCursosCarrera(String carrera, String codigoCurso) throws SQLException {
        this.dao.eliminarCurso(carrera, codigoCurso);
    }

}