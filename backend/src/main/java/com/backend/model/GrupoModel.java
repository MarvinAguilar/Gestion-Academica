package com.backend.model;

import com.backend.datamodel.data.GrupoDAO;
import org.json.JSONArray;
import org.json.JSONObject;

public class GrupoModel {

    private static GrupoModel instance = null;
    private final GrupoDAO dao;

    public GrupoModel() {
        this.dao = GrupoDAO.getInstance();
    }

    public static GrupoModel getInstance(){
        if (instance == null){
            instance = new GrupoModel();
        }
        return instance;
    }

    public JSONArray listarGrupo(){
        return this.dao.listarGrupo();
    }

    public JSONObject buscarGrupo(int numero, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo){
        return this.dao.buscarGrupo(numero, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    }

    public void insertarGrupo(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo){
        this.dao.insertarGrupo(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    }

    public void modificarGrupo(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo){
        this.dao.modificarGrupo(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
    }

    public void eliminarGrupo(int numero, String codigoCurso, int annoCiclo, int numeroCiclo){
        this.dao.eliminarGrupo(numero, codigoCurso, annoCiclo, numeroCiclo);
    }
    
}
