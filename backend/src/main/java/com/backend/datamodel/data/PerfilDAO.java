package com.backend.model.data;

import com.backend.model.entity.Perfil;
import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfilDAO {
    private static PerfilDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static PerfilDAO getInstance() {
        if (instance == null) instance = new PerfilDAO();
        return instance;
    }

    public void insertarPerfil(int id, String nombre) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(PerfilCRUD.INSERTARPERFIL);
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
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


    public void modificarPerfil(int id, String nombre) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(PerfilCRUD.MODIFICARPERFIL);
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
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

    public void eliminarPerfil(int id) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(PerfilCRUD.ELIMINARPERFIL);
            stmt.setInt(1, id);
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

    public JSONObject buscarPerfil(int id) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONObject perfil = new JSONObject();
        try {
            stmt = connection.prepareCall(PerfilCRUD.BUSCARPERFIL);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setInt(2, id);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                perfil.put("id", rs.getInt("id"));
                perfil.put("nombre", rs.getString("nombre"));
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

        return perfil;
    }

    public JSONArray listarPerfil() {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONArray perfiles = new JSONArray();
        try {
            stmt = connection.prepareCall(PerfilCRUD.LISTARPERFIL);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                JSONObject perfil = new JSONObject();
                perfil.put("id", rs.getInt("id"));
                perfil.put("nombre", rs.getString("nombre"));
                perfiles.put(perfil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return perfiles;
    }
}
