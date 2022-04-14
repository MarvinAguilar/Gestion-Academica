package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class CicloDAO {
    private static CicloDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static CicloDAO getInstance() {
        if (instance == null) instance = new CicloDAO();
        return instance;
    }

    public void insertarCiclo(int anno, int numero, String fechaInicio, String fechaFinal, int estado) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CicloCRUD.INSERTARCICLO);
            stmt.setInt(1, anno);
            stmt.setInt(2, numero);
            stmt.setDate(3, Date.valueOf(fechaInicio));
            stmt.setDate(4, Date.valueOf(fechaFinal));
            stmt.setInt(5, estado);
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

    public void modificarCiclo(int anno, int numero, String fechaInicio, String fechaFinal, int estado) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CicloCRUD.MODIFICARCICLO);
            stmt.setInt(1, anno);
            stmt.setInt(2, numero);
            stmt.setDate(3, Date.valueOf(fechaInicio));
            stmt.setDate(4, Date.valueOf(fechaFinal));
            stmt.setInt(5, estado);
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

    public void eliminarCiclo(int anno, int numero) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CicloCRUD.ELIMINARCICLO);
            stmt.setInt(1, anno);
            stmt.setInt(2, numero);
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

    public JSONObject buscarCiclo(int anno, int numero) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONObject ciclo = new JSONObject();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(CicloCRUD.BUSCARCICLO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setInt(2, anno);
            stmt.setInt(3, numero);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                ciclo.put("anno", rs.getInt("anno"));
                ciclo.put("numero", rs.getInt("numero"));
                ciclo.put("fechaInicio", rs.getDate("fechaInicio").toString());
                ciclo.put("fechaFinal", rs.getDate("fechaFinal").toString());
                ciclo.put("estado", rs.getInt("estado"));
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

        return ciclo;
    }

    public JSONArray listarCiclo() {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONArray ciclos = new JSONArray();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(CicloCRUD.LISTARCICLO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                JSONObject ciclo = new JSONObject();
                ciclo.put("anno", rs.getInt("anno"));
                ciclo.put("fechaInicio", rs.getDate("fechaInicio").toString());
                ciclo.put("fechaFinal", rs.getDate("fechaFinal").toString());
                ciclo.put("estado", rs.getInt("estado"));
                ciclos.put(ciclo);
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

        return ciclos;
    }
}
