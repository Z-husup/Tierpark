package com.prog.tierpark.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=Tierpark;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
        
        Connection conn = DriverManager.getConnection(url);

        return conn;
    }
}


