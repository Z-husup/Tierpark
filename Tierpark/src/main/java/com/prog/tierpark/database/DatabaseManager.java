package com.prog.tierpark.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true;databaseName=Tierpark;encrypt=true;trustServerCertificate=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}


