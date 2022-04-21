package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoDAO {
    private static CursoDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static CursoDAO getInstance() {
        if (instance == null) instance = new CursoDAO();
        return instance;
    }

    public void insertarCurso(String codigo, String nombre, int creditos, int horasSemanales, String carrera) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CursoCRUD.INSERTARCURSO);
        stmt.setString(1, codigo);
        stmt.setString(2, nombre);
        stmt.setInt(3, creditos);
        stmt.setInt(4, horasSemanales);
        stmt.executeUpdate();
        stmt.close();
        CursosCarreraDAO.getInstance().insertarCursoCarrera(carrera, codigo, 2022, 1);
    }

    public void modificarCurso(String codigo, String nombre, int creditos, int horasSemanales) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CursoCRUD.MODIFICARCURSO);
        stmt.setString(1, codigo);
        stmt.setString(2, nombre);
        stmt.setInt(3, creditos);
        stmt.setInt(4, horasSemanales);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarCurso(String codigo) throws SQLException {
        JSONObject cursoCarrera = CursosCarreraDAO.getInstance().buscarCursoCarrera(codigo);
        CursosCarreraDAO.getInstance().eliminarCurso(cursoCarrera.getString("carrera"), codigo);
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CursoCRUD.ELIMINARCURSO);
        stmt.setString(1, codigo);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarCurso(String codigo) throws SQLException {
        JSONObject curso = new JSONObject();
        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CursoCRUD.BUSCARCURSO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, codigo);
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

    public JSONArray listarCurso() throws SQLException {

        JSONArray cursos = new JSONArray();
        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CursoCRUD.LISTARCURSO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);

        while (rs.next()) {
            JSONObject curso = new JSONObject();
            JSONObject cursoCarrera = CursosCarreraDAO.getInstance().buscarCursoCarrera(rs.getString("codigo"));
            JSONObject carrera = CarreraDAO.getInstance().buscarCarrera(cursoCarrera.getString("carrera"));
            curso.put("codigo", rs.getString("codigo"));
            curso.put("nombre", rs.getString("nombre"));
            curso.put("creditos", rs.getInt("creditos"));
            curso.put("horasSemanales", rs.getInt("horasSemanales"));
            curso.put("carrera", carrera.getString("nombre"));
            cursos.put(curso);
        }
        rs.close();
        stmt.close();
        return cursos;
    }
}
