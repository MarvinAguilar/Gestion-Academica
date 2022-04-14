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

    public void insertarCarrera(String codigo, String nombre, String titulo) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CarreraCRUD.INSERTARCARRERA);
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setString(3, titulo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void modificarCarrera(String codigo, String nombre, String titulo) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CarreraCRUD.MODIFICARCARRERA);
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setString(3, titulo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminarCarrera(String codigo) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CarreraCRUD.ELIMINARCARRERA);
            stmt.setString(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject buscarCarrera(String codigo) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONObject carrera = new JSONObject();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(CarreraCRUD.BUSCARCARRERA);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, codigo);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                carrera.put("codigo", rs.getString("codigo"));
                carrera.put("nombre", rs.getString("nombre"));
                carrera.put("titulo", rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return carrera;
    }

    public JSONArray listarCarrera() {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONArray carreras = new JSONArray();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(CarreraCRUD.LISTARCARRERA);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                JSONObject carrera = new JSONObject();
                carrera.put("codigo", rs.getString("codigo"));
                carrera.put("nombre", rs.getString("nombre"));
                carrera.put("titulo", rs.getString("nombre"));
                carreras.put(carrera);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return carreras;
    }
}
