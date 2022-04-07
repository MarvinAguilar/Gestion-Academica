package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Alumno;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    private static AlumnoDAO instance = null;
    private DBConnection db;

    private AlumnoDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static AlumnoDAO getInstance() {
        if (instance == null) {
            instance = new AlumnoDAO();
        }
        return instance;
    }

    public void insertarAlumno(Alumno alumno) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(AlumnoCRUD.INSERTARALUMNO);

            stmt.setString(1, alumno.getCedula());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getTelefono());
            stmt.setString(4, alumno.getEmail());
            stmt.setDate(5, Date.valueOf(alumno.getFechaNacimiento()));
            stmt.setString(6, alumno.getCarrera());

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


    public void modificarAlumno(Alumno alumno) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(AlumnoCRUD.MODIFICARALUMNO);

            stmt.setString(1, alumno.getCedula());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getTelefono());
            stmt.setString(4, alumno.getEmail());
            stmt.setDate(5, Date.valueOf(alumno.getFechaNacimiento()));
            stmt.setString(6, alumno.getCarrera());

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

    public void eliminarAlumno(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(AlumnoCRUD.ELIMINARALUMNO);
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

    public Alumno buscarAlumno(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Alumno alumno = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(AlumnoCRUD.BUSCARALUMNO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, cedula);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                alumno = new Alumno(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getString("carrera"));
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

    public List<Alumno> listarAlumno() {
        List<Alumno> alumnos = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(AlumnoCRUD.LISTARALUMNO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                alumnos.add(new Alumno(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getString("carrera")));
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
