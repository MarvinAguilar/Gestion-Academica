package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class UsuarioDAO {
    private static UsuarioDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static UsuarioDAO getInstance() {
        if (instance == null) instance = new UsuarioDAO();
        return instance;
    }

    public void insertarUsuario(String cedula, String clave, int perfil) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(UsuarioCRUD.INSERTARUSUARIO);
        stmt.setString(1, cedula);
        stmt.setString(2, clave);
        stmt.setInt(3, perfil);
        stmt.executeUpdate();
        stmt.close();
    }


    public void modificarUsuario(String cedula, String clave) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(UsuarioCRUD.MODIFICARUSUARIO);
        stmt.setString(1, cedula);
        stmt.setString(2, clave);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarUsuario(String cedula) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(UsuarioCRUD.ELIMINARUSUARIO);
        stmt.setString(1, cedula);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarUsuario(String cedula) throws SQLException {
        JSONObject usuario = new JSONObject();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(UsuarioCRUD.BUSCARUSUARIO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, cedula);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            usuario.put("cedula", rs.getString("cedula"));
            usuario.put("clave", rs.getString("clave"));
            usuario.put("perfil", rs.getInt("perfil"));
        }
        rs.close();
        stmt.close();

        return usuario;
    }

    public JSONArray listarUsuario() throws SQLException {
        JSONArray usuarios = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(UsuarioCRUD.LISTARUSUARIO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);

        while (rs.next()) {
            JSONObject usuario = new JSONObject();
            usuario.put("cedula", rs.getString("cedula"));
            usuario.put("clave", rs.getString("clave"));
            usuario.put("perfil", rs.getInt("perfil"));
            usuarios.put(usuario);
        }
        rs.close();
        stmt.close();

        return usuarios;
    }

    public String login(String cedula, String clave) throws SQLException {
        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(UsuarioCRUD.LOGIN);
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.setString(2, cedula);
        stmt.setString(3, clave);
        stmt.execute();

        String authorization = stmt.getString(1);

        stmt.close();

        return authorization;
    }
}
