package com.prog.tierpark.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Utility class for managing the database connection to the Tierpark MSSQL database.
 * <p>
 * Provides a static method to obtain a JDBC connection using integrated security.
 */
public class DatabaseManager {

    /**
     * JDBC connection URL for the local SQL Server instance using integrated security.
     * <p>
     * This URL connects to the 'Tierpark' database on the 'SQLEXPRESS' instance
     * and uses Windows authentication.
     */
    private static final String URL =
            "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true;databaseName=Tierpark;encrypt=true;trustServerCertificate=true";

    /**
     * Establishes and returns a connection to the MSSQL database.
     *
     * @return A {@link Connection} to the Tierpark database.
     * @throws SQLException If the connection fails.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

