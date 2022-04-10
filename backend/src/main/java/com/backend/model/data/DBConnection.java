package com.backend.model.data;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String DB_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "GestionAcademica";
    private static final String DB_PASSWORD = "root";

    private static DBConnection instance = null;
    private Connection connection;

    private DBConnection() {
        try {
            Class.forName(DB_DRIVER_CLASS);
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception ex) {
            System.err.println("Couldn't connect to database");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
