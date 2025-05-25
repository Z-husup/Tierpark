package com.prog.tierpark.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static Connection getConnection() throws SQLException {

        String url = "{DB_URL}";

        Connection conn = DriverManager.getConnection(url);

        return conn;
    }
}


