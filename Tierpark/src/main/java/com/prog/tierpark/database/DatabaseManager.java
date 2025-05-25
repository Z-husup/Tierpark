package com.prog.tierpark.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:tierpark.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            String schema = Files.readString(Paths.get("src/main/resources/schema.sql"));
            stmt.executeUpdate(schema);
            System.out.println("Database initialized.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

