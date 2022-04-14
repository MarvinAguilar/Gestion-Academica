package com.backend.model;

import com.backend.services.EstudianteGrupoDAO;
import org.json.JSONArray;
import org.json.JSONObject;

public class EstudianteGrupoModel {
    private static EstudianteGrupoModel instance = null;
    private final EstudianteGrupoDAO dao;

    public EstudianteGrupoModel() {
        this.dao = EstudianteGrupoDAO.getInstance();
    }

    public static EstudianteGrupoModel getInstance() {
        if (instance == null) instance = new EstudianteGrupoModel();
        return instance;
    }

    public JSONObject listarEstudiantesGrupo(int numeroGrupo, String codigoCurso, int annoCiclo, int numeroCiclo) {
        return this.dao.listarEstudiantesGrupo(numeroGrupo, codigoCurso, annoCiclo, numeroCiclo);
    }

//   public JSONObject buscarEstudianteGrupo(String codigo){
//        return this.dao.buscarEstudianteGrupo(codigo);
//    }

//    public void insertarEstudianteGrupo(String codigo, String nombre, int creditos, int horasSemanales){
//        this.dao.insertarEstudianteGrupo(codigo, nombre, creditos, horasSemanales);
//    }

//    public void modificarEstudianteGrupo(String codigo, String nombre, int creditos, int horasSemanales){
//        this.dao.modificarEstudianteGrupo(codigo, nombre, creditos, horasSemanales);
//    }

//    public void eliminarEstudianteGrupo(String codigo){
//        this.dao.eliminarEstudianteGrupo(codigo);
//    }

}
