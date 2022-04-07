package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Carrera;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {
    private static CarreraDAO instance = null;
    private DBConnection db;

    private CarreraDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static CarreraDAO getInstance() {
        if (instance == null) {
            instance = new CarreraDAO();
        }
        return instance;
    }

    public void insertarCarrera(Carrera carrera) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CarreraCRUD.INSERTARCARRERA);

            stmt.setString(1, carrera.getCodigo());
            stmt.setString(2, carrera.getNombre());
            stmt.setString(3, carrera.getTitulo());

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


    public void modificarCarrera(Carrera carrera) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CarreraCRUD.MODIFICARCARRERA);

            stmt.setString(1, carrera.getCodigo());
            stmt.setString(2, carrera.getNombre());
            stmt.setString(3, carrera.getTitulo());

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

    public void eliminarCarrera(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CarreraCRUD.ELIMINARCARRERA);
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

    public Carrera buscarCarrera(String codigo) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Carrera Carrera = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CarreraCRUD.BUSCARCARRERA);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, codigo);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                Carrera = new Carrera(rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("titulo"));
            }

            if (Carrera == null) throw new NoDataException("No se han encontrado los datos solicitados");
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

        return Carrera;
    }

    public List<Carrera> listarCarrera() {
        List<Carrera> carreras = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CarreraCRUD.LISTARCARRERA);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                carreras.add(new Carrera(rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("titulo")));
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
        return carreras;
    }
}
