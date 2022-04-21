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

    public void insertarProfesor(String cedula, String nombre, String telefono, String email) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(ProfesorCRUD.INSERTARPROFESOR);
        stmt.setString(1, cedula);
        stmt.setString(2, nombre);
        stmt.setString(3, telefono);
        stmt.setString(4, email);
        stmt.executeUpdate();
        stmt.close();
        UsuarioDAO.getInstance().insertarUsuario(cedula, "12345", 3);
    }

    public void modificarProfesor(String cedula, String nombre, String telefono, String email) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(ProfesorCRUD.MODIFICARPROFESOR);
        stmt.setString(1, cedula);
        stmt.setString(2, nombre);
        stmt.setString(3, telefono);
        stmt.setString(4, email);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarProfesor(String cedula) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(ProfesorCRUD.ELIMINARPROFESOR);
        stmt.setString(1, cedula);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarProfesor(String cedula) throws SQLException {
        JSONObject profesor = new JSONObject();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(ProfesorCRUD.BUSCARPROFESOR);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, cedula);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            profesor.put("cedula", rs.getString("cedula"));
            profesor.put("nombre", rs.getString("nombre"));
            profesor.put("telefono", rs.getString("telefono"));
            profesor.put("email", rs.getString("email"));
        }
        rs.close();
        stmt.close();

        return profesor;
    }

    public JSONArray listarProfesor() throws SQLException {
        JSONArray profesores = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(ProfesorCRUD.LISTARPROFESOR);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            JSONObject profesor = new JSONObject();
            profesor.put("cedula", rs.getString("cedula"));
            profesor.put("nombre", rs.getString("nombre"));
            profesor.put("telefono", rs.getString("telefono"));
            profesor.put("email", rs.getString("email"));
            profesores.put(profesor);
        }
        rs.close();
        stmt.close();

        return profesores;
    }
}
