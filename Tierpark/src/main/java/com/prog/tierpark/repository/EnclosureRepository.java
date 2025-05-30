package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Enclosure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for performing CRUD operations on {@link Enclosure} entities.
 */
public class EnclosureRepository {

    /**
     * Retrieves all enclosures from the database.
     *
     * @return A list of {@link Enclosure} objects.
     */
    public List<Enclosure> getAllEnclosures() {
        List<Enclosure> list = new ArrayList<>();
        String sql = "SELECT * FROM enclosure";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractEnclosure(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieves an enclosure by its ID.
     *
     * @param id The ID of the enclosure.
     * @return The matching {@link Enclosure}, or {@code null} if not found.
     */
    public Enclosure getEnclosureById(long id) {
        String sql = "SELECT * FROM enclosure WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractEnclosure(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Inserts a new enclosure into the database.
     *
     * @param enclosure The {@link Enclosure} to add.
     * @return {@code true} if the insert was successful.
     */
    public boolean addEnclosure(Enclosure enclosure) {
        String sql = """
                INSERT INTO enclosure (name, zone, status, type, capacity, description, condition, area)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setEnclosureFields(pstmt, enclosure, false);

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    enclosure.setId(keys.getLong(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates an existing enclosure in the database.
     *
     * @param enclosure The updated {@link Enclosure} object.
     * @return {@code true} if the update was successful.
     */
    public boolean updateEnclosure(Enclosure enclosure) {
        String sql = """
                UPDATE enclosure SET name=?, zone=?, status=?, type=?, capacity=?, description=?, condition=?, area=?
                WHERE id=?
                """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setEnclosureFields(pstmt, enclosure, true);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes an enclosure from the database by its ID.
     *
     * @param id The ID of the enclosure to delete.
     * @return {@code true} if deletion was successful.
     */
    public boolean deleteEnclosure(long id) {
        String sql = "DELETE FROM enclosure WHERE id=?";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Extracts an {@link Enclosure} from a {@link ResultSet} row.
     *
     * @param rs The {@link ResultSet} positioned at a row.
     * @return An {@link Enclosure} object.
     * @throws SQLException If column retrieval fails.
     */
    private Enclosure extractEnclosure(ResultSet rs) throws SQLException {
        return new Enclosure(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("zone"),
                rs.getString("status"),
                rs.getString("type"),
                rs.getInt("capacity"),
                rs.getString("description"),
                rs.getString("condition"),
                rs.getString("area")
        );
    }

    /**
     * Sets values from an {@link Enclosure} into a {@link PreparedStatement}.
     *
     * @param pstmt     The statement to populate.
     * @param e         The enclosure object.
     * @param includeId If true, appends the ID at the end for UPDATE operations.
     * @throws SQLException If statement binding fails.
     */
    private void setEnclosureFields(PreparedStatement pstmt, Enclosure e, boolean includeId) throws SQLException {
        pstmt.setString(1, e.getName());
        pstmt.setString(2, e.getZone());
        pstmt.setString(3, e.getStatus());
        pstmt.setString(4, e.getType());
        pstmt.setInt(5, e.getCapacity());
        pstmt.setString(6, e.getDescription());
        pstmt.setString(7, e.getCondition());
        pstmt.setString(8, e.getArea());

        if (includeId) {
            pstmt.setLong(9, e.getId());
        }
    }
}


