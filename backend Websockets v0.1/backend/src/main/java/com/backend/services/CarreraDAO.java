package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarreraDAO {
    private static CarreraDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static CarreraDAO getInstance() {
        if (instance == null) instance = new CarreraDAO();
        return instance;
    }

    public void insertarCarrera(String codigo, String nombre, String titulo) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CarreraCRUD.INSERTARCARRERA);
        stmt.setString(1, codigo);
        stmt.setString(2, nombre);
        stmt.setString(3, titulo);
        stmt.executeUpdate();
        stmt.close();
    }

    public void modificarCarrera(String codigo, String nombre, String titulo) throws SQLException {

        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CarreraCRUD.MODIFICARCARRERA);
        stmt.setString(1, codigo);
        stmt.setString(2, nombre);
        stmt.setString(3, titulo);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarCarrera(String codigo) throws SQLException {
        CallableStatement stmt = null;
        connection.setAutoCommit(true);
        stmt = connection.prepareCall(CarreraCRUD.ELIMINARCARRERA);
        stmt.setString(1, codigo);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarCarrera(String codigo) throws SQLException {

        JSONObject carrera = new JSONObject();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CarreraCRUD.BUSCARCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, codigo);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            carrera.put("codigo", rs.getString("codigo"));
            carrera.put("nombre", rs.getString("nombre"));
            carrera.put("titulo", rs.getString("titulo"));
        }
        rs.close();
        stmt.close();
        return carrera;
    }

    public JSONArray listarCarrera() throws SQLException {

        JSONArray carreras = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CarreraCRUD.LISTARCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            JSONObject carrera = new JSONObject();
            carrera.put("codigo", rs.getString("codigo"));
            carrera.put("nombre", rs.getString("nombre"));
            carrera.put("titulo", rs.getString("titulo"));
            carreras.put(carrera);
        }
        rs.close();
        stmt.close();
        return carreras;
    }
}
