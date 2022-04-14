package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private static UsuarioDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static UsuarioDAO getInstance() {
        if (instance == null) instance = new UsuarioDAO();
        return instance;
    }

    public void insertarUsuario(String cedula, String nombre, int perfil) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(UsuarioCRUD.INSERTARUSUARIO);
            stmt.setString(1, cedula);
            stmt.setString(2, nombre);
            stmt.setInt(3, perfil);
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


    public void modificarUsuario(String cedula, String nombre, int perfil) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(UsuarioCRUD.MODIFICARUSUARIO);
            stmt.setString(1, cedula);
            stmt.setString(2, nombre);
            stmt.setInt(3, perfil);
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

    public void eliminarUsuario(String cedula) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(UsuarioCRUD.ELIMINARUSUARIO);
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

    public JSONObject buscarUsuario(String cedula) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONObject usuario = new JSONObject();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(UsuarioCRUD.BUSCARUSUARIO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, cedula);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                usuario.put("cedula", rs.getString("cedula"));
                usuario.put("clave", rs.getString("clave"));
                usuario.put("perfil", rs.getInt("perfil"));
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

        return usuario;
    }

    public JSONArray listarUsuario() {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONArray usuarios = new JSONArray();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(UsuarioCRUD.LISTARUSUARIO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                JSONObject usuario = new JSONObject();
                usuario.put("cedula", rs.getString("cedula"));
                usuario.put("clave", rs.getString("clave"));
                usuario.put("perfil", rs.getInt("perfil"));
                usuarios.put(usuario);
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

        return usuarios;
    }
}
