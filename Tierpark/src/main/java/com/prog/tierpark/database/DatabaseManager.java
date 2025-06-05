package com.prog.tierpark.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Utility class for managing the database connection and schema execution
 * for the Tierpark MSSQL database.
 */
public class DatabaseManager {

    /**
     * JDBC URL for connecting to the local SQL Server instance using integrated security.
     * <p>
     * This connection string targets the 'Tierpark' database on the 'SQLEXPRESS' instance.
     */
    public static final String URL =
             "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true;databaseName=tierpark;encrypt=true;trustServerCertificate=true";
//            "jdbc:sqlserver://localhost:1433;databaseName=tierpark;user=sa;password=~ZD6JcPHaDZJ;encrypt=true;trustServerCertificate=true;";

    /**
     * Establishes and returns a JDBC connection to the database.
     *
     * @return A {@link Connection} object connected to the Tierpark database.
     * @throws SQLException If the connection cannot be established.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Executes the SQL schema file (typically {@code schema.sql}) found in the resources folder.
     * <p>
     * The method reads and splits the SQL file by semicolons to execute each command separately.
     * Prints a success message when completed, or an error trace if it fails.
     */
    public static void executeSchema() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = loadSchemaSql();
            for (String command : sql.split(";")) {
                if (!command.trim().isEmpty()) {
                    stmt.execute(command.trim());
                }
            }

            sql = loadMockSql();
            for (String command : sql.split(";")) {
                if (!command.trim().isEmpty()) {
                    stmt.execute(command.trim());
                }
            }

            System.out.println("✅ schema.sql executed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the contents of the {@code schema.sql} file from the classpath.
     *
     * @return The complete SQL script as a {@link String}.
     * @throws RuntimeException If the file cannot be found or read.
     */
    private static String loadSchemaSql() {
        try (InputStream in = DatabaseManager.class.getClassLoader().getResourceAsStream("schema.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("❌ Could not load schema.sql", e);
        }
    }

    private static String loadMockSql() {
        try (InputStream in = DatabaseManager.class.getClassLoader().getResourceAsStream("mock.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("❌ Could not load schema.sql", e);
        }
    }
}


