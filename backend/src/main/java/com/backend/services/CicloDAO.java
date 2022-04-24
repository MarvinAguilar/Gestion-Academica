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

    public void insertarCiclo(int anno, int numero, String fechaInicio, String fechaFinal, String estado) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CicloCRUD.INSERTARCICLO);
        stmt.setInt(1, anno);
        stmt.setInt(2, numero);
        stmt.setDate(3, Date.valueOf(fechaInicio));
        stmt.setDate(4, Date.valueOf(fechaFinal));
        stmt.setString(5, estado);
        stmt.executeUpdate();
        stmt.close();
    }

    public void modificarCiclo(int anno, int numero, String fechaInicio, String fechaFinal, String estado) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CicloCRUD.MODIFICARCICLO);
        stmt.setInt(1, anno);
        stmt.setInt(2, numero);
        stmt.setDate(3, Date.valueOf(fechaInicio));
        stmt.setDate(4, Date.valueOf(fechaFinal));
        stmt.setString(5, estado);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarCiclo(int anno, int numero) throws SQLException {

        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CicloCRUD.ELIMINARCICLO);
        stmt.setInt(1, anno);
        stmt.setInt(2, numero);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarCiclo(int anno, int numero) throws SQLException {

        JSONObject ciclo = new JSONObject();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CicloCRUD.BUSCARCICLO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setInt(2, anno);
        stmt.setInt(3, numero);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            ciclo.put("anno", rs.getInt("anno"));
            ciclo.put("numero", rs.getInt("numero"));
            ciclo.put("fechaInicio", rs.getDate("fechaInicio").toString());
            ciclo.put("fechaFinal", rs.getDate("fechaFinal").toString());
            ciclo.put("estado", rs.getString("estado"));
        }
        rs.close();
        stmt.close();
        return ciclo;
    }

    public JSONArray listarCiclo() throws SQLException {

        JSONArray ciclos = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CicloCRUD.LISTARCICLO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            JSONObject ciclo = new JSONObject();
            ciclo.put("anno", rs.getInt("anno"));
            ciclo.put("numero", rs.getInt("numero"));
            ciclo.put("fechaInicio", rs.getDate("fechaInicio").toString());
            ciclo.put("fechaFinal", rs.getDate("fechaFinal").toString());
            ciclo.put("estado", rs.getString("estado"));
            ciclos.put(ciclo);
        }
        rs.close();
        stmt.close();
        return ciclos;
    }
}
