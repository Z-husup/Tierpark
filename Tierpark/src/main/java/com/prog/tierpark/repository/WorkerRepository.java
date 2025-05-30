package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.model.enums.WorkerSpecialization;
import com.prog.tierpark.model.enums.WorkerStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for accessing and managing {@link Worker} entities,
 * including their associated {@link Enclosure}.
 */
public class WorkerRepository {

    /**
     * Retrieves all workers from the database, including associated enclosures.
     *
     * @return A list of {@link Worker} objects.
     */
    public List<Worker> getAllWorkers() {
        List<Worker> workers = new ArrayList<>();
        String sql = """
            SELECT 
                worker.*, 
                enclosure.id          AS enclosure_id,
                enclosure.name        AS enclosure_name,
                enclosure.zone,
                enclosure.status      AS enclosure_status,
                enclosure.type,
                enclosure.capacity,
                enclosure.description,
                enclosure.condition,
                enclosure.area
            FROM worker
            LEFT JOIN enclosure ON worker.enclosure = enclosure.id
            """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                workers.add(extractWorkerWithEnclosure(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workers;
    }

    /**
     * Retrieves a worker by ID, including associated enclosure.
     *
     * @param id The worker's ID.
     * @return A {@link Worker} object, or {@code null} if not found.
     */
    public Worker getWorkerById(long id) {
        String sql = """
            SELECT 
                worker.*, 
                enclosure.id          AS enclosure_id,
                enclosure.name        AS enclosure_name,
                enclosure.zone,
                enclosure.status      AS enclosure_status,
                enclosure.type,
                enclosure.capacity,
                enclosure.description,
                enclosure.condition,
                enclosure.area
            FROM worker
            LEFT JOIN enclosure ON worker.enclosure = enclosure.id
            WHERE worker.id = ?
            """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractWorkerWithEnclosure(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Extracts a {@link Worker} from a {@link ResultSet}, including its {@link Enclosure}.
     *
     * @param rs The result set positioned at a row.
     * @return The corresponding {@link Worker}.
     * @throws SQLException If a column cannot be accessed.
     */
    private Worker extractWorkerWithEnclosure(ResultSet rs) throws SQLException {
        Enclosure enclosure = null;

        if (rs.getLong("enclosure_id") != 0) {
            enclosure = new Enclosure(
                    rs.getLong("enclosure_id"),
                    rs.getString("enclosure_name"),
                    rs.getString("zone"),
                    rs.getString("enclosure_status"),
                    rs.getString("type"),
                    rs.getInt("capacity"),
                    rs.getString("description"),
                    rs.getString("condition"),
                    rs.getString("area")
            );
        }

        return new Worker(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("fullName"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                rs.getDate("dateOfBirth").toLocalDate(),
                rs.getString("gender"),
                rs.getDate("hireDate").toLocalDate(),
                WorkerStatus.valueOf(rs.getString("status").toUpperCase()),
                rs.getInt("salary"),
                WorkerSpecialization.valueOf(rs.getString("specialization").toUpperCase()),
                enclosure
        );
    }
}
