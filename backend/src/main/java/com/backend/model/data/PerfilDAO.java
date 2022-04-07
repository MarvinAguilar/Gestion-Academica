package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Perfil;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO {

    private static PerfilDAO instance = null;
    private DBConnection db;

    private PerfilDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static PerfilDAO getInstance() {
        if (instance == null) {
            instance = new PerfilDAO();
        }
        return instance;
    }

    public void insertarPerfil(Perfil perfil) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(PerfilCRUD.LISTARPERFIL);

            stmt.setInt(1, perfil.getId());
            stmt.setString(2, perfil.getNombre());

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


    public void modificarPerfil(Perfil perfil) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(PerfilCRUD.MODIFICARPERFIL);

            stmt.setInt(1, perfil.getId());
            stmt.setString(2, perfil.getNombre());

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

    public void eliminarPerfil(int id) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(PerfilCRUD.ELIMINARPERFIL);
            stmt.setInt(1, id);

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

    public Perfil buscarPerfil(int id) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Perfil perfil = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(PerfilCRUD.BUSCARPERFIL);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setInt(2, id);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                perfil = new Perfil(rs.getInt("id"),
                        rs.getString("nombre"));
            }

            if (perfil == null) throw new NoDataException("No se han encontrado los datos solicitados");
        } catch (SQLException | ClassNotFoundException | NoDataException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return perfil;
    }

    public List<Perfil> listarPerfil() {
        List<Perfil> perfiles = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(PerfilCRUD.LISTARPERFIL);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                perfiles.add(new Perfil(rs.getInt("id"),
                        rs.getString("nombre")));
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
        return perfiles;
    }

}
