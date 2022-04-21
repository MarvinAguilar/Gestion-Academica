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

    public void modificaCursoCarrera(String carrera, String curso, int anno, int numero) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.MODIFICARCURSOCARRERA);
        stmt.setString(1, carrera);
        stmt.setString(2, curso);
        stmt.setInt(3, anno);
        stmt.setInt(4, numero);

        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarCurso(String carrera, String codigoCurso) throws SQLException {

        JSONObject curso = new JSONObject();
        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.BUSCARCURSOSCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, carrera);
        stmt.setString(3, codigoCurso);

        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        curso.put("carrera", carrera);
        while (rs.next()) {
            JSONObject cursoAux = CursoDAO.getInstance().buscarCurso(rs.getString("codigoCurso"));
            curso.put("codigo", cursoAux.getString("codigo"));
            curso.put("nombre", cursoAux.getString("nombre"));
            curso.put("creditos", cursoAux.getInt("creditos"));
            curso.put("horasSemanales", cursoAux.getInt("horasSemanales"));
        }
        rs.close();
        stmt.close();
        return curso;
    }

    public JSONObject buscarCursoCarrera(String codigoCurso) throws SQLException {

        JSONObject curso = new JSONObject();
        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.BUSCARCURSOCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, codigoCurso);

        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);

        while (rs.next()) {
            curso.put("carrera", rs.getString("codigoCarrera"));
        }
        rs.close();
        stmt.close();
        return curso;
    }

    public JSONObject listarCurso(String codigoCarrera) throws SQLException {
        JSONObject carrera = new JSONObject();
        JSONArray cursos = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(CursosCarreraCRUD.LISTARCURSOCARRERA);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, codigoCarrera);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        carrera.put("carrera", codigoCarrera);
        while (rs.next()) {
            JSONObject curso = CursoDAO.getInstance().buscarCurso(rs.getString("codigoCurso"));

            cursos.put(curso);
        }
        carrera.put("cursos", cursos);
        rs.close();
        stmt.close();
        return carrera;
    }
}
