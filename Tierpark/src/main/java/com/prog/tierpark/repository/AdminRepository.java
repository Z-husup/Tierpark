package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for performing CRUD operations on the {@code admin} table.
 * <p>
 * Connects to the Tierpark database using SQL Server and provides
 * methods for fetching, inserting, updating, and deleting Admin records.
 */
public class AdminRepository {

    /**
     * Retrieves all admin records from the database.
     *
     * @return A list of {@link Admin} objects.
     */
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT id, username, password FROM admin";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                admins.add(new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }

    /**
     * Retrieves an admin record by its ID.
     *
     * @param id The ID of the admin.
     * @return The matching {@link Admin} object, or {@code null} if not found.
     */
    public Admin getAdminById(int id) {
        String sql = "SELECT id, username, password FROM admin WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Inserts a new admin record into the database.
     *
     * @param admin The {@link Admin} object to insert.
     * @return {@code true} if the operation succeeded; otherwise {@code false}.
     */
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admin (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    admin.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates an existing admin record in the database.
     *
     * @param admin The {@link Admin} object containing updated data.
     * @return {@code true} if the update succeeded; otherwise {@code false}.
     */
    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE admin SET username = ?, password = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());
            pstmt.setInt(3, admin.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes an admin record by its ID.
     *
     * @param id The ID of the admin to delete.
     * @return {@code true} if the deletion was successful; otherwise {@code false}.
     */
    public boolean deleteAdmin(int id) {
        String sql = "DELETE FROM admin WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}


