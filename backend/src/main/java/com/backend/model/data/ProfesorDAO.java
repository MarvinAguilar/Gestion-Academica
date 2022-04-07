package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Profesor;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {

    private static ProfesorDAO instance = null;
    private DBConnection db;

    private ProfesorDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static ProfesorDAO getInstance() {
        if (instance == null) {
            instance = new ProfesorDAO();
        }
        return instance;
    }

    public void insertarProfesor(Profesor profesor) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(ProfesorCRUD.INSERTARPROFESOR);

            stmt.setString(1, profesor.getCedula());
            stmt.setString(2, profesor.getNombre());
            stmt.setString(3, profesor.getTelefono());
            stmt.setString(4, profesor.getEmail());

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


    public void modificarProfesor(Profesor profesor) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(ProfesorCRUD.MODIFICARPROFESOR);

            stmt.setString(1, profesor.getCedula());
            stmt.setString(2, profesor.getNombre());
            stmt.setString(3, profesor.getTelefono());
            stmt.setString(4, profesor.getEmail());

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

    public void eliminarProfesor(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(ProfesorCRUD.ELIMINARPROFESOR);
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

    public Profesor buscarProfesor(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Profesor profesor = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(ProfesorCRUD.BUSCARPROFESOR);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, cedula);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                profesor = new Profesor(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"));
            }

            if (profesor == null) throw new NoDataException("No se han encontrado los datos solicitados");
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

        return profesor;
    }

    public List<Profesor> listarProfesor() {
        List<Profesor> profesores = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(ProfesorCRUD.LISTARPROFESOR);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                profesores.add(new Profesor(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")));
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
        return profesores;
    }
}
