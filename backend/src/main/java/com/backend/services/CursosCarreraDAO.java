package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursosCarreraDAO {

    private static CursosCarreraDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static CursosCarreraDAO getInstance() {
        if (instance == null) instance = new CursosCarreraDAO();
        return instance;
    }

    public void insertarCursoCarrera(String carrera, String curso, int annio, int numeroCiclo) throws SQLException {

        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.INSERTARCURSOCARRERA);
        stmt.setString(1, carrera);
        stmt.setString(2, curso);
        stmt.setInt(3, annio);
        stmt.setInt(4, numeroCiclo);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarCurso(String carrera, String curso) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.ELIMINARCURSOCARRERA);
        stmt.setString(1, carrera);
        stmt.setString(2, curso);

        stmt.executeUpdate();
        stmt.close();

    }

    public JSONObject buscarCurso(String carrera, String codigoCurso) throws SQLException {

        JSONObject curso = new JSONObject();
        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.BUSCARCURSOCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, carrera);

        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            curso.put("codigo", rs.getString("codigo"));
            curso.put("nombre", rs.getString("nombre"));
            curso.put("creditos", rs.getInt("creditos"));
            curso.put("horasSemanales", rs.getInt("horasSemanales"));
        }
        rs.close();
        stmt.close();
        return curso;
    }

    public JSONArray listarCurso(String carrera) throws SQLException {

        JSONArray cursos = new JSONArray();
        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.LISTARCURSOCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, carrera);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);

        while (rs.next()) {
            JSONObject curso = new JSONObject();
            curso.put("codigo", rs.getString("codigo"));
            curso.put("nombre", rs.getString("nombre"));
            curso.put("creditos", rs.getInt("creditos"));
            curso.put("horasSemanales", rs.getInt("horasSemanales"));
            cursos.put(curso);
        }
        rs.close();
        stmt.close();
        return cursos;
    }
}
