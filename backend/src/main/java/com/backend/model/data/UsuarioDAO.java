package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Usuario;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static UsuarioDAO instance = null;
    private DBConnection db;

    private UsuarioDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    public void insertarUsuario(Usuario usuario) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(UsuarioCRUD.INSERTARUSUARIO);

            stmt.setString(1, usuario.getCedula());
            stmt.setString(2, usuario.getNombre());
            stmt.setInt(3, usuario.getPerfil());

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


    public void modificarUsuario(Usuario usuario) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(UsuarioCRUD.MODIFICARUSUARIO);

            stmt.setString(1, usuario.getCedula());
            stmt.setString(2, usuario.getNombre());
            stmt.setInt(3, usuario.getPerfil());

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

    public void eliminarUsuario(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(UsuarioCRUD.ELIMINARUSUARIO);
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

    public Usuario buscarUsuario(String cedula) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Usuario usuario = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(UsuarioCRUD.BUSCARUSUARIO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, cedula);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                usuario = new Usuario(rs.getString("cedula"),
                        rs.getString("clave"),
                        rs.getInt("perfil"));
            }

            if (usuario == null) throw new NoDataException("No se han encontrado los datos solicitados");
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

        return usuario;
    }

    public List<Usuario> listarUsuario() {
        List<Usuario> usuarios = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(UsuarioCRUD.LISTARUSUARIO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                usuarios.add(new Usuario(rs.getString("cedula"),
                        rs.getString("clave"),
                        rs.getInt("perfil")));
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
        return usuarios;
    }

}
