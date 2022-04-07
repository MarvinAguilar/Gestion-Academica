package com.backend.dbconfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

//    private static final String CONFIGURATION_PATH = "db.properties";
    private static final String DB_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "GestionAcademica";
    private static final String DB_PASSWORD = "root";
    private static DBConnection instance = null;
//    private Properties configuration = null;

    private DBConnection() throws IOException {
//        this.configuration = new Properties();
//        configuration.load(getClass().getResourceAsStream(CONFIGURATION_PATH));
//        DB_DRIVER_CLASS = this.configuration.getProperty("DB_DRIVER_CLASS");
//        DB_URL = this.configuration.getProperty("DB_URL");
//        DB_USER = this.configuration.getProperty("DB_USER");
//        DB_PASSWORD = this.configuration.getProperty("DB_PASSWORD");
    }

    public static DBConnection getInstance() throws IOException {
        if (instance == null) {
            try {
                instance = new DBConnection();
            } catch (IOException e) {
                System.err.printf("Excepción: '%s'%n", e.getMessage());
                throw e;
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            Class.forName(DB_DRIVER_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage() + " - Driver not loaded");
        } catch (SQLException e) {
            throw new SQLException(e.getMessage() + " - Connection failure");
        }
        return con;
    }
}
