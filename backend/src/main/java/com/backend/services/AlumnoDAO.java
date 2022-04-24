package com.backend.services;

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

    public void insertarAlumno(String cedula, String nombre, String telefono, String email, String fechaNacimiento, String carrera) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(AlumnoCRUD.INSERTARALUMNO);
        stmt.setString(1, cedula);
        stmt.setString(2, nombre);
        stmt.setString(3, telefono);
        stmt.setString(4, email);
        stmt.setDate(5, Date.valueOf(fechaNacimiento));
        stmt.setString(6, carrera);
        stmt.executeUpdate();
        stmt.close();
        UsuarioDAO.getInstance().insertarUsuario(cedula, "12345", 4);
    }

    public void modificarAlumno(String cedula, String nombre, String telefono, String email, String fechaNacimiento, String carrera) throws SQLException {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(AlumnoCRUD.MODIFICARALUMNO);
        stmt.setString(1, cedula);
        stmt.setString(2, nombre);
        stmt.setString(3, telefono);
        stmt.setString(4, email);
        stmt.setDate(5, Date.valueOf(fechaNacimiento));
        stmt.setString(6, carrera);
        stmt.executeUpdate();
        stmt.close();
    }

    public void eliminarAlumno(String cedula) throws SQLException  {
        connection.setAutoCommit(true);
        CallableStatement stmt = connection.prepareCall(AlumnoCRUD.ELIMINARALUMNO);
        stmt.setString(1, cedula);
        stmt.executeUpdate();
        stmt.close();
    }

    public JSONObject buscarAlumno(String cedula) throws SQLException {
        JSONObject alumno = new JSONObject();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(AlumnoCRUD.BUSCARALUMNO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.setString(2, cedula);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            alumno.put("cedula", rs.getString("cedula"));
            alumno.put("nombre", rs.getString("nombre"));
            alumno.put("telefono", rs.getString("telefono"));
            alumno.put("email", rs.getString("email"));
            alumno.put("fechaNacimiento", rs.getDate("fechaNacimiento").toString());
            alumno.put("carrera", rs.getString("carrera"));
        }
        rs.close();
        stmt.close();

        return alumno;
    }

    public JSONArray listarAlumno() throws SQLException {

        JSONArray alumnos = new JSONArray();

        connection.setAutoCommit(false);
        CallableStatement stmt = connection.prepareCall(AlumnoCRUD.LISTARALUMNO);
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        stmt.execute();

        ResultSet rs = (ResultSet) stmt.getObject(1);
        while (rs.next()) {
            JSONObject alumno = new JSONObject();
            alumno.put("cedula", rs.getString("cedula"));
            alumno.put("nombre", rs.getString("nombre"));
            alumno.put("telefono", rs.getString("telefono"));
            alumno.put("email", rs.getString("email"));
            alumno.put("fechaNacimiento", rs.getDate("fechaNacimiento").toString());
            alumno.put("codigoCarrera", rs.getString("codigoCarrera"));
            alumno.put("nombreCarrera", rs.getString("nombreCarrera"));
            alumnos.put(alumno);
        }
        rs.close();
        stmt.close();

        return alumnos;
    }
}
