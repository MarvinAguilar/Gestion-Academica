package com.backend.services;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstudianteGrupoDAO {
    private static EstudianteGrupoDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static EstudianteGrupoDAO getInstance() {
        if (instance == null) instance = new EstudianteGrupoDAO();
        return instance;
    }

    public void matriculaEstudiante(String cedulaEstudiante, int numeroGrupo, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(EstudianteGrupoCRUD.MATRICULAESTUDIANTE);
        stmt.setString(1, cedulaEstudiante);
        stmt.setInt(2, numeroGrupo);
        stmt.setString(3, codigoCurso);
        stmt.setInt(4, annoCiclo);
        stmt.setInt(5, numeroCiclo);
        stmt.setFloat(6, 0);
        stmt.executeUpdate();
        stmt.close();
    }


    public void ingresaNota(String cedulaEstudiante, int numeroGrupo, String codigoCurso, int annoCiclo, int numeroCiclo, Float nota) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(EstudianteGrupoCRUD.INGRESANOTA);
        stmt.setString(1, cedulaEstudiante);
        stmt.setInt(2, numeroGrupo);
        stmt.setString(3, codigoCurso);
        stmt.setInt(4, annoCiclo);
        stmt.setInt(5, numeroCiclo);
        stmt.setFloat(6, nota);
        stmt.executeUpdate();
        stmt.close();
    }

    public void desmatriculaEstudiante(String cedulaEstudiante, int numeroGrupo, String codigoCurso) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(EstudianteGrupoCRUD.DESMATRICULAESTUDIANTE);
        stmt.setString(1, cedulaEstudiante);
        stmt.setInt(2, numeroGrupo);
        stmt.setString(3, codigoCurso);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONArray buscarGruposEstudiante(String cedulaEstudiante) throws SQLException {
        JSONArray grupos = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(EstudianteGrupoCRUD.BUSCARGRUPOSESTUDIANTE);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, cedulaEstudiante);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            JSONObject grupo = new JSONObject();
            JSONObject curso = CursoDAO.getInstance().buscarCurso(rs.getString("codigoCurso"));

            String nombreCurso = curso.getString("nombre");
            int creditosCurso = curso.getInt("creditos");

            grupo.put("numeroCiclo", rs.getInt("numeroCiclo"));
            grupo.put("annoCiclo", rs.getString("annoCiclo"));
            grupo.put("codigoCurso", rs.getString("codigoCurso"));
            grupo.put("nombreCurso", nombreCurso);
            grupo.put("creditosCurso", creditosCurso);
            grupo.put("nota", rs.getFloat("nota"));
            grupos.put(grupo);
        }

        rs.close();
        stmt.close();

        return grupos;
    }

    public JSONObject listarEstudiantesGrupo(int numeroGrupo, String codigoCurso, int annoCiclo, int numeroCiclo) throws SQLException {
        JSONObject grupo = new JSONObject();
        JSONArray estudiantes = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(EstudianteGrupoCRUD.LISTARESTUDIANTESGRUPO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setInt(2, numeroGrupo);
        stmt.setString(3, codigoCurso);
        stmt.setInt(4, annoCiclo);
        stmt.setInt(5, numeroCiclo);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        grupo.put("numeroGrupo", numeroGrupo);
        grupo.put("codigoCurso", codigoCurso);
        grupo.put("annoCiclo", annoCiclo);
        grupo.put("numeroCiclo", numeroCiclo);
        while (rs.next()) {
            JSONObject estudiante = AlumnoDAO.getInstance().buscarAlumno(rs.getString("cedulaEstudiante"));
            estudiantes.put(estudiante);
        }
        grupo.put("estudiantes", estudiantes);

        rs.close();
        stmt.close();

        return grupo;
    }
}
