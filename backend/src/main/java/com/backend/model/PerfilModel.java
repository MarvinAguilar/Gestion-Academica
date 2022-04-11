package com.backend.model;

import com.backend.datamodel.data.PerfilDAO;
import org.json.JSONArray;
import org.json.JSONObject;

public class PerfilModel {

    private static PerfilModel instance = null;
    private final PerfilDAO dao;

    public PerfilModel() {
        this.dao = PerfilDAO.getInstance();
    }

    public static PerfilModel getInstance(){
        if (instance == null){
            instance = new PerfilModel();
        }
        return instance;
    }

    public JSONArray listarPerfil(){
        return this.dao.listarPerfil();
    }

    public JSONObject buscarPerfil(int id){
        return this.dao.buscarPerfil(id);
    }

    public void insertarPerfil(int id, String nombre){
        this.dao.insertarPerfil(id, nombre);
    }

    public void modificarPerfil(int id, String nombre){
        this.dao.modificarPerfil(id, nombre);
    }

    public void eliminarPerfil(int id){
        this.dao.eliminarPerfil(id);
    }
    
}
