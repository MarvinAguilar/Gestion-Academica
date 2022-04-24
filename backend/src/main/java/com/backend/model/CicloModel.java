package com.backend.model;


import com.backend.services.CicloDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public class CicloModel {
    private static CicloModel instance = null;
    private final CicloDAO dao;

    public CicloModel() {
        this.dao = CicloDAO.getInstance();
    }

    public static CicloModel getInstance() {
        if (instance == null) instance = new CicloModel();
        return instance;
    }

    public JSONArray listarCiclo() throws SQLException {
        return this.dao.listarCiclo();
    }

    public JSONObject buscarCiclo(int anno, int numero) throws SQLException {
        return this.dao.buscarCiclo(anno, numero);
    }

    public void insertarCiclo(int anno, int numero, String fechaInicio, String fechaFinal, String estado) throws SQLException {
        this.dao.insertarCiclo(anno, numero, fechaInicio, fechaFinal, estado);
    }

    public void modificarCiclo(int anno, int numero, String fechaInicio, String fechaFinal, String estado) throws SQLException {
        this.dao.modificarCiclo(anno, numero, fechaInicio, fechaFinal, estado);
    }

    public void eliminarCiclo(int anno, int numero) throws SQLException {
        this.dao.eliminarCiclo(anno, numero);
    }
}
