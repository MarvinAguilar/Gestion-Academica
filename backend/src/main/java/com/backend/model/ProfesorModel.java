package com.backend.model;

import com.backend.datamodel.data.ProfesorDAO;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfesorModel {

    private static ProfesorModel instance = null;
    private final ProfesorDAO dao;

    public ProfesorModel() {
        this.dao = ProfesorDAO.getInstance();
    }

    public static ProfesorModel getInstance(){
        if (instance == null){
            instance = new ProfesorModel();
        }
        return instance;
    }

    public JSONArray listarProfesor(){
        return this.dao.listarProfesor();
    }

    public JSONObject buscarProfesor(String cedula){
        return this.dao.buscarProfesor(cedula);
    }

    public void insertarProfesor(String cedula, String nombre, String telefono, String email){
        this.dao.insertarProfesor(cedula, nombre, telefono, email);
    }

    public void modificarProfesor(String cedula, String nombre, String telefono, String email){
        this.dao.modificarProfesor(cedula, nombre, telefono, email);
    }

    public void eliminarProfesor(String cedula){
        this.dao.eliminarProfesor(cedula);
    }
    
}
