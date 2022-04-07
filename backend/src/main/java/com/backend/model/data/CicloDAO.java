package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Ciclo;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CicloDAO {

    private static CicloDAO instance = null;
    private DBConnection db;

    private CicloDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static CicloDAO getInstance() {
        if (instance == null) {
            instance = new CicloDAO();
        }
        return instance;
    }

    public void insertarCiclo(Ciclo ciclo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CicloCRUD.INSERTARCICLO);

            stmt.setInt(1, ciclo.getAnno());
            stmt.setInt(2, ciclo.getNumero());
            stmt.setDate(3, Date.valueOf(ciclo.getFechaInicio()));
            stmt.setDate(4, Date.valueOf(ciclo.getFechafinal()));
            stmt.setInt(5, ciclo.getEstado());

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


    public void modificarCiclo(Ciclo ciclo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CicloCRUD.MODIFICARCICLO);

            stmt.setInt(1, ciclo.getAnno());
            stmt.setInt(2, ciclo.getNumero());
            stmt.setDate(3, Date.valueOf(ciclo.getFechaInicio()));
            stmt.setDate(4, Date.valueOf(ciclo.getFechafinal()));
            stmt.setInt(5, ciclo.getEstado());

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

    public void eliminarCiclo(int anno, int numero) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CicloCRUD.ELIMINARCICLO);
            stmt.setInt(1, anno);
            stmt.setInt(2, numero);

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

    public Ciclo buscarCiclo(int anno, int numero) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Ciclo ciclo = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CicloCRUD.BUSCARCICLO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setInt(2, anno);
            stmt.setInt(3, numero);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                ciclo = new Ciclo(rs.getInt("anno"),
                        rs.getInt("numero"),
                        rs.getDate("fechaInicio").toLocalDate(),
                        rs.getDate("fechaFinal").toLocalDate(),
                        rs.getInt("estado"));
            }

            if (ciclo == null) throw new NoDataException("No se han encontrado los datos solicitados");
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

        return ciclo;
    }

    public List<Ciclo> listarCiclo() {
        List<Ciclo> ciclos = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(CicloCRUD.LISTARCICLO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                ciclos.add(new Ciclo(rs.getInt("anno"),
                        rs.getInt("numero"),
                        rs.getDate("fechaInicio").toLocalDate(),
                        rs.getDate("fechaFinal").toLocalDate(),
                        rs.getInt("estado")));
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
        return ciclos;
    }

}
