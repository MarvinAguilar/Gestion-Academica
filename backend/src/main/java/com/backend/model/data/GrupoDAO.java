package com.backend.model.data;

import com.backend.dbconfig.DBConnection;
import com.backend.exception.NoDataException;
import com.backend.model.entity.Grupo;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    private static GrupoDAO instance = null;
    private DBConnection db;

    private GrupoDAO() {
        try {
            db = DBConnection.getInstance();
        } catch (IOException e) {
            System.err.printf("Excepción: '%s'%n", e.getMessage());
            System.exit(-1);
        }
    }

    public static GrupoDAO getInstance() {
        if (instance == null) {
            instance = new GrupoDAO();
        }
        return instance;
    }

    public void insertarGrupo(Grupo grupo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(GrupoCRUD.INSERTARGRUPO);

            stmt.setInt(1, grupo.getNumero());
            stmt.setString(2, grupo.getHorario());
            stmt.setString(3, grupo.getProfesor());
            stmt.setString(4, grupo.getCurso());
            stmt.setInt(5, grupo.getAnnoCiclo());
            stmt.setInt(6, grupo.getNumeroCiclo());

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


    public void modificarGrupo(Grupo grupo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(GrupoCRUD.MODIFICARGRUPO);

            stmt.setInt(1, grupo.getNumero());
            stmt.setString(2, grupo.getHorario());
            stmt.setString(3, grupo.getProfesor());
            stmt.setString(4, grupo.getCurso());
            stmt.setInt(5, grupo.getAnnoCiclo());
            stmt.setInt(6, grupo.getNumeroCiclo());

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

    public void eliminarGrupo(String numero, String curso, int annoCiclo, int numeroCiclo) {
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(GrupoCRUD.ELIMINARGRUPO);
            stmt.setString(1, numero);
            stmt.setString(2, curso);
            stmt.setInt(3, annoCiclo);
            stmt.setInt(4, numeroCiclo);

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

    public Grupo buscarGrupo(String numero, String curso, int annoCiclo, int numeroCiclo) {
        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        Grupo Grupo = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(GrupoCRUD.BUSCARGRUPO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, numero);
            stmt.setString(3, curso);
            stmt.setInt(4, annoCiclo);
            stmt.setInt(5, numeroCiclo);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                Grupo = new Grupo(rs.getInt("numero"),
                        rs.getString("horario"),
                        rs.getString("profesor"),
                        rs.getString("curso"),
                        rs.getInt("annoCurso"),
                        rs.getInt("numeroCurso")
                );
            }

            if (Grupo == null) throw new NoDataException("No se han encontrado los datos solicitados");
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

        return Grupo;
    }

    public List<Grupo> listarGrupo() {
        List<Grupo> Grupos = new ArrayList<>();

        Connection con = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareCall(GrupoCRUD.LISTARGRUPO);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                Grupos.add(new Grupo(rs.getInt("numero"),
                        rs.getString("horario"),
                        rs.getString("profesor"),
                        rs.getString("curso"),
                        rs.getInt("annoCurso"),
                        rs.getInt("numeroCurso")));
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
        return Grupos;
    }
    
}
