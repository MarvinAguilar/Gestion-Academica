package com.backend.model;

import com.backend.datamodel.data.CursosCarreraDAO;
import org.json.JSONArray;
import org.json.JSONObject;

public class CursosCarreraModel {

    private static CursosCarreraModel instance = null;
    private final CursosCarreraDAO dao;

    public CursosCarreraModel() {
        this.dao = CursosCarreraDAO.getInstance();
    }

    public static CursosCarreraModel getInstance(){
        if (instance == null){
            instance = new CursosCarreraModel();
        }
        return instance;
    }

    //public JSONArray listarCursosCarrera(){
    //    return this.dao.listarCursosCarrera();
    //}

    //public JSONObject buscarCursosCarrera(int numero, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo){
    //    return this.dao.buscarCursosCarrera(numero, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    //}

    //public void insertarCursosCarrera(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo){
    //    this.dao.insertarCursosCarrera(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    //}

    //public void modificarCursosCarrera(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo){
    //    this.dao.modificarCursosCarrera(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    //}

    //public void eliminarCursosCarrera(int numero, String codigoCurso, int annoCiclo, int numeroCiclo){
    //    this.dao.eliminarCursosCarrera(numero, codigoCurso, annoCiclo, numeroCiclo);
    //}
    
}
