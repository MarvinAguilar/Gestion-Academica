package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Alumno;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteGrupoDAO {
    private static EstudianteGrupoDAO instance = null;
    private DBConnection db;

    private EstudianteGrupoDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static EstudianteGrupoDAO getInstance() {
        if (instance == null) {
            instance = new EstudianteGrupoDAO();
        }
        return instance;
    }

    public void insertarEstudianteGrupo(String cedula, String grupo, String curso, int annoCiclo, int numeroCiclo, Float nota) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(EstudianteGrupoCRUD.INSERTARESTUDIANTEGRUPO);

            stmt.setString(1, cedula);
            stmt.setString(2, grupo);
            stmt.setString(3, curso);
            stmt.setInt(4, annoCiclo);
            stmt.setInt(5, numeroCiclo);
            stmt.setFloat(6, nota);

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


    public void modificarEstudianteGrupo(String cedula, String grupo, String curso, int annoCiclo, int numeroCiclo, Float nota) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(EstudianteGrupoCRUD.MODIFICARESTUDIANTEGRUPO);

            stmt.setString(1, cedula);
            stmt.setString(2, grupo);
            stmt.setString(3, curso);
            stmt.setInt(4, annoCiclo);
            stmt.setInt(5, numeroCiclo);
            stmt.setFloat(6, nota);

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

    public void eliminarEstudianteGrupo(String cedula, String grupo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(EstudianteGrupoCRUD.ELIMINARESTUDIANTEGRUPO);
            stmt.setString(1, cedula);
            stmt.setString(2, grupo);
            stmt.setInt(3, 0);

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

    public Alumno buscarEstudianteGrupo(String cedula, String grupo, String curso, int annoCiclo, int numeroCiclo) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Alumno alumno = null;


        try {
            con = db.getConnection();
            stmt = con.prepareCall(EstudianteGrupoCRUD.BUSCARESTUDIANTEGRUPO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, cedula);
            stmt.setString(3, grupo);
            stmt.setString(4, curso);
            stmt.setInt(5, annoCiclo);
            stmt.setInt(6, numeroCiclo);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                alumno = AlumnoDAO.getInstance().buscarAlumno(rs.getString("cedulaEstudiante"));



            }

            if (alumno == null) throw new NoDataException("No se han encontrado los datos solicitados");
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

        return alumno;
    }

    public List<Alumno> listarEstudianteGrupo(String grupo, String curso, int annoCiclo, int numeroCiclo) {
        List<Alumno> alumnos = new ArrayList<>();
        Alumno alumno = null;

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(EstudianteGrupoCRUD.LISTARESTUDIANTEGRUPO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, grupo);
            stmt.setString(3, curso);
            stmt.setInt(4, annoCiclo);
            stmt.setInt(5, numeroCiclo);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                alumno = AlumnoDAO.getInstance().buscarAlumno(rs.getString("cedulaEstudiante"));

                alumnos.add(alumno);
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
        return alumnos;
    }

}
