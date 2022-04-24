package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoDAO {
    private static GrupoDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static GrupoDAO getInstance() {
        if (instance == null) instance = new GrupoDAO();
        return instance;
    }

    public void insertarGrupo(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(GrupoCRUD.INSERTARGRUPO);
        stmt.setInt(1, numero);
        stmt.setString(2, horario);
        stmt.setString(3, cedulaProfesor);
        stmt.setString(4, codigoCurso);
        stmt.setInt(5, annoCiclo);
        stmt.setInt(6, numeroCiclo);
        stmt.executeUpdate();
        stmt.close();
    }

    public void modificarGrupo(int numero, String horario, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(GrupoCRUD.MODIFICARGRUPO);
        stmt.setInt(1, numero);
        stmt.setString(2, horario);
        stmt.setString(3, cedulaProfesor);
        stmt.setString(4, codigoCurso);
        stmt.setInt(5, annoCiclo);
        stmt.setInt(6, numeroCiclo);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarGrupo(int numero, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(GrupoCRUD.ELIMINARGRUPO);
        stmt.setInt(1, numero);
        stmt.setString(2, codigoCurso);
        stmt.setInt(3, annoCiclo);
        stmt.setInt(4, numeroCiclo);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarGrupo(int numero, String cedulaProfesor, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        JSONObject grupo = new JSONObject();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(GrupoCRUD.BUSCARGRUPO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setInt(2, numero);
        stmt.setString(3, cedulaProfesor);
        stmt.setString(4, codigoCurso);
        stmt.setInt(5, annoCiclo);
        stmt.setInt(6, numeroCiclo);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            grupo.put("numero", rs.getInt("numero"));
            grupo.put("horario", rs.getString("horario"));
            grupo.put("cedulaProfesor", rs.getString("cedulaProfesor"));
            grupo.put("codigoCurso", rs.getString("codigoCurso"));
            grupo.put("annoCiclo", rs.getInt("annoCiclo"));
            grupo.put("numeroCiclo", rs.getInt("numeroCiclo"));
        }
        rs.close();
        stmt.close();

        return grupo;
    }

    public JSONArray listarGrupo(String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        JSONArray grupos = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(GrupoCRUD.LISTARGRUPO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, codigoCurso);
        stmt.setInt(3, annoCiclo);
        stmt.setInt(4, numeroCiclo);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            JSONObject grupo = new JSONObject();
            grupo.put("numero", rs.getInt("numero"));
            grupo.put("horario", rs.getString("horario"));
            grupo.put("cedulaProfesor", rs.getString("cedulaProfesor"));
            grupo.put("nombreProfesor", rs.getString("nombreProfesor"));
            grupo.put("codigoCurso", rs.getString("codigoCurso"));
            grupo.put("nombreCurso", rs.getString("nombreCurso"));
            grupo.put("annoCiclo", rs.getInt("annoCiclo"));
            grupo.put("numeroCiclo", rs.getInt("numeroCiclo"));
            grupos.put(grupo);
        }
        rs.close();
        stmt.close();

        return grupos;
    }

    public JSONArray listarGrupoCarrera(String codigoCarrera, int annoCiclo, int numeroCiclo) throws SQLException {
        JSONArray grupos = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(GrupoCRUD.LISTARGRUPOCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, codigoCarrera);
        stmt.setInt(3, annoCiclo);
        stmt.setInt(4, numeroCiclo);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            JSONObject grupo = new JSONObject();
            grupo.put("numeroGrupo", rs.getInt("numeroGrupo"));
            grupo.put("horario", rs.getString("horario"));
            grupo.put("creditos", rs.getInt("creditos"));
            grupo.put("horasSemanales", rs.getInt("horasSemanales"));
            grupo.put("cedulaProfesor", rs.getString("cedulaProfesor"));
            grupo.put("nombreProfesor", rs.getString("nombreProfesor"));
            grupo.put("codigoCurso", rs.getString("codigoCurso"));
            grupo.put("nombreCurso", rs.getString("nombreCurso"));
            grupo.put("annoCiclo", rs.getInt("annoCiclo"));
            grupo.put("numeroCiclo", rs.getInt("numeroCiclo"));
            grupos.put(grupo);
        }
        rs.close();
        stmt.close();

        return grupos;
    }
}
