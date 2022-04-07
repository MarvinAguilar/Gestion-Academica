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

public class CursoDAO {
    private static CursoDAO instance = null;
    private DBConnection db;

    private CursoDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static CursoDAO getInstance() {
        if (instance == null) {
            instance = new CursoDAO();
        }
        return instance;
    }

    public void insertarCurso(Curso curso) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursoCRUD.INSERTARCURSO);

            stmt.setString(1, curso.getCodigo());
            stmt.setString(2, curso.getNombre());
            stmt.setInt(3, curso.getCreditos());
            stmt.setInt(4, curso.getHorasSemanales());

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


    public void modificarCurso(Curso curso) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursoCRUD.MODIFICARCURSO);

            stmt.setString(1, curso.getCodigo());
            stmt.setString(2, curso.getNombre());
            stmt.setInt(3, curso.getCreditos());
            stmt.setInt(4, curso.getHorasSemanales());

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

    public void eliminarCurso(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursoCRUD.ELIMINARCURSO);
            stmt.setString(1, cedula);

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

    public Curso buscarCurso(String codigo) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Curso Curso = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursoCRUD.BUSCARCURSO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, codigo);
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

    public List<Curso> listarCurso() {
        List<Curso> cursos = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CursoCRUD.LISTARCURSO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                cursos.add(new Curso(rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("creditos"),
                        rs.getInt("horasSemanales")));
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
