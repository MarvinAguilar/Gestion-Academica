package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfesorDAO {
    private static ProfesorDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static ProfesorDAO getInstance() {
        if (instance == null) instance = new ProfesorDAO();
        return instance;
    }

    public void insertarProfesor(String cedula, String nombre, String telefono, String email) {
        CallableStatement stmt = null;
        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(ProfesorCRUD.INSERTARPROFESOR);
            stmt.setString(1, cedula);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
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

    public void modificarProfesor(String cedula, String nombre, String telefono, String email) {
        CallableStatement stmt = null;
        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(ProfesorCRUD.MODIFICARPROFESOR);
            stmt.setString(1, cedula);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
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

    public void eliminarProfesor(String cedula) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(ProfesorCRUD.ELIMINARPROFESOR);
            stmt.setString(1, cedula);
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

    public JSONObject buscarProfesor(String cedula) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONObject profesor = new JSONObject();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(ProfesorCRUD.BUSCARPROFESOR);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, cedula);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                profesor.put("cedula", rs.getString("cedula"));
                profesor.put("nombre", rs.getString("nombre"));
                profesor.put("telefono", rs.getString("telefono"));
                profesor.put("email", rs.getString("email"));
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

        return profesor;
    }

    public JSONArray listarProfesor() {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONArray profesores = new JSONArray();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(ProfesorCRUD.LISTARPROFESOR);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                JSONObject profesor = new JSONObject();
                profesor.put("cedula", rs.getString("cedula"));
                profesor.put("nombre", rs.getString("nombre"));
                profesor.put("telefono", rs.getString("telefono"));
                profesor.put("email", rs.getString("email"));
                profesores.put(profesor);
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

        return profesores;
    }
}
