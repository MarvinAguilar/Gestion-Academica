package com.backend.services;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CursosCarreraDAO {

    private static CursosCarreraDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static CursosCarreraDAO getInstance() {
        if (instance == null) instance = new CursosCarreraDAO();
        return instance;
    }

    public void insertarCursoCarrera(String carrera, String curso, int annio, int numeroCiclo) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CursosCarreraCRUD.INSERTARCURSOCARRERA);
            stmt.setString(1, carrera);
            stmt.setString(2, curso);
            stmt.setInt(3, annio);
            stmt.setInt(4, numeroCiclo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminarCurso(String carrera, String curso, int annio, int numeroCiclo) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(CursosCarreraCRUD.ELIMINARCURSOCARRERA);
            stmt.setString(1, carrera);
            stmt.setString(2, curso);
            stmt.setInt(3, annio);
            stmt.setInt(4, numeroCiclo);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public Curso buscarCurso(String carrera, String curso, int annio, int numeroCiclo) {
//        CallableStatement stmt = null;
//        ResultSet rs = null;
//
//        Curso Curso = null;
//
//        try {
//            stmt = connection.prepareCall(CursosCarreraCRUD.BUSCARCURSOCARRERA);
//            stmt.registerOutParameter(1, OracleTypes.CURSOR);
//            stmt.setString(2, carrera);
//            stmt.setString(3, curso);
//            stmt.setInt(4, annio);
//            stmt.setInt(5, numeroCiclo);
//            stmt.execute();
//
//            rs = (ResultSet) stmt.getObject(1);
//
//            while (rs.next()) {
//                Curso = new Curso(rs.getString("codigo"),
//                        rs.getString("nombre"),
//                        rs.getInt("creditos"),
//                        rs.getInt("horasSemanales"));
//            }
//
//            if (Curso == null) throw new Exception("No se han encontrado los datos solicitados");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (stmt != null) stmt.close();
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return Curso;
//    }
//
//    public List<Curso> listarCurso(String carrera) {
//        List<Curso> cursos = new ArrayList<>();
//
//        Curso curso = null;
//
//        CallableStatement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            stmt = connection.prepareCall(CursosCarreraCRUD.LISTARCURSOCARRERA);
//            stmt.registerOutParameter(1, OracleTypes.CURSOR);
//            stmt.setString(2, carrera);
//            stmt.execute();
//
//            rs = (ResultSet) stmt.getObject(1);
//
//            while (rs.next()) {
//
//                curso = CursoDAO.getInstance().buscarCurso(rs.getString("codigoCurso"));
//
//                cursos.add(curso);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (stmt != null) stmt.close();
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return cursos;
//    }
}
