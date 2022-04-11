package com.backend.model.data;

import com.backend.model.entity.Curso;
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

    public void insertarCurso(String codigo, String nombre, int creditos, int horasSemanales) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CursoCRUD.INSERTARCURSO);
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setInt(3, creditos);
            stmt.setInt(4, horasSemanales);
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

    public void modificarCurso(String codigo, String nombre, int creditos, int horasSemanales) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CursoCRUD.MODIFICARCURSO);
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setInt(3, creditos);
            stmt.setInt(4, horasSemanales);
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

    public void eliminarCurso(String codigo) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CursoCRUD.ELIMINARCURSO);
            stmt.setString(1, codigo);
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

    public JSONObject buscarCurso(String codigo) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONObject curso = new JSONObject();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(CursoCRUD.BUSCARCURSO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, codigo);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                curso.put("codigo", rs.getString("codigo"));
                curso.put("nombre", rs.getString("nombre"));
                curso.put("creditos", rs.getInt("creditos"));
                curso.put("horasSemanales", rs.getInt("horasSemanales"));
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

        return curso;
    }

    public JSONArray listarCurso() {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONArray cursos = new JSONArray();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(CursoCRUD.LISTARCURSO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                JSONObject curso = new JSONObject();
                curso.put("codigo", rs.getString("codigo"));
                curso.put("nombre", rs.getString("nombre"));
                curso.put("creditos", rs.getInt("creditos"));
                curso.put("horasSemanales", rs.getInt("horasSemanales"));
                cursos.put(curso);
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

        return cursos;
    }
}
