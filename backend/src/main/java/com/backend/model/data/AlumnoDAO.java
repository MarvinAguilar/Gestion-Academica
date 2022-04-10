package com.backend.model.data;

import oracle.jdbc.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class AlumnoDAO {
    private static AlumnoDAO instance = null;
    private final Connection connection = DBConnection.getInstance().getConnection();

    public static AlumnoDAO getInstance() {
        if (instance == null) instance = new AlumnoDAO();
        return instance;
    }

    public void insertarAlumno(String cedula, String nombre, String telefono, String email, String fechaNacimiento, String carrera) {
        CallableStatement stmt = null;
        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(AlumnoCRUD.INSERTARALUMNO);
            stmt.setString(1, cedula);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setDate(5, Date.valueOf(fechaNacimiento));
            stmt.setString(6, carrera);
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

    public void modificarAlumno(String cedula, String nombre, String telefono, String email, String fechaNacimiento, String carrera) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(AlumnoCRUD.MODIFICARALUMNO);
            stmt.setString(1, cedula);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setDate(5, Date.valueOf(fechaNacimiento));
            stmt.setString(6, carrera);
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

    public void eliminarAlumno(String cedula) {
        CallableStatement stmt = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.prepareCall(AlumnoCRUD.ELIMINARALUMNO);
            stmt.setString(1, cedula);
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

    public JSONObject buscarAlumno(String cedula) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONObject alumno = new JSONObject();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(AlumnoCRUD.BUSCARALUMNO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, cedula);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                alumno.put("cedula", rs.getString("cedula"));
                alumno.put("nombre", rs.getString("nombre"));
                alumno.put("telefono", rs.getString("telefono"));
                alumno.put("email", rs.getString("email"));
                alumno.put("fechaNacimiento", rs.getDate("fechaNacimiento").toString());
                alumno.put("carrera", rs.getString("carrera"));
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

        return alumno;
    }

    public JSONArray listarAlumno() {
        CallableStatement stmt = null;
        ResultSet rs = null;

        JSONArray alumnos = new JSONArray();
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareCall(AlumnoCRUD.LISTARALUMNO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                JSONObject alumno = new JSONObject();
                alumno.put("cedula", rs.getString("cedula"));
                alumno.put("nombre", rs.getString("nombre"));
                alumno.put("telefono", rs.getString("telefono"));
                alumno.put("email", rs.getString("email"));
                alumno.put("fechaNacimiento", rs.getDate("fechaNacimiento").toString());
                alumno.put("carrera", rs.getString("carrera"));
                alumnos.put(alumno);
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

        return alumnos;
    }
}
