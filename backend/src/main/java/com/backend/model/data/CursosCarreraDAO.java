package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Curso;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursosCarreraDAO {

    private static CursosCarreraDAO instance = null;
    private DBConnection db;

    private CursosCarreraDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static CursosCarreraDAO getInstance() {
        if (instance == null) {
            instance = new CursosCarreraDAO();
        }
        return instance;
    }

    public void insertarCursoCarrera(String carrera, String curso, int annio, int numeroCiclo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursosCarreraCRUD.INSERTARCURSOCARRERA);

            stmt.setString(1, carrera);
            stmt.setString(2, curso);
            stmt.setInt(3, annio);
            stmt.setInt(4, numeroCiclo);

            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminarCurso(String carrera, String curso, int annio, int numeroCiclo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursosCarreraCRUD.ELIMINARCURSOCARRERA);
            stmt.setString(1, carrera);
            stmt.setString(2, curso);
            stmt.setInt(3, annio);
            stmt.setInt(4, numeroCiclo);

            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Curso buscarCurso(String carrera, String curso, int annio, int numeroCiclo) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Curso Curso = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursosCarreraCRUD.BUSCARCURSOCARRERA);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, carrera);
            stmt.setString(3, curso);
            stmt.setInt(4, annio);
            stmt.setInt(5, numeroCiclo);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                Curso = new Curso(rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("creditos"),
                        rs.getInt("horasSemanales"));
            }

            if (Curso == null) throw new NoDataException("No se han encontrado los datos solicitados");
        } catch (SQLException | ClassNotFoundException | NoDataException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return Curso;
    }

    public List<Curso> listarCurso(String carrera) {
        List<Curso> cursos = new ArrayList<>();

        Curso curso = null;

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursosCarreraCRUD.LISTARCURSOCARRERA);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, carrera);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {

                curso = CursoDAO.getInstance().buscarCurso(rs.getString("codigoCurso"));

                cursos.add(curso);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cursos;
    }

}
