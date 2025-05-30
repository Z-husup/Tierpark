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


    /**
     * Retrieves a worker by their username from the database, including associated enclosure information.
     *
     * @param username the username of the worker to retrieve
     * @return a {@link Worker} object if found, or {@code null} if no such worker exists
     */
    public Worker getWorkerByUsername(String username) {
        String sql = """
        SELECT worker.*, 
               enclosure.id AS enclosure_id, enclosure.name AS enclosure_name, enclosure.zone, 
               enclosure.status AS enclosure_status, enclosure.type,
               enclosure.capacity, enclosure.description, enclosure.condition, enclosure.area
        FROM worker
        LEFT JOIN enclosure ON worker.enclosure = enclosure.id
        WHERE worker.username = ?
        """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
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
     * Adds a new worker to the database and assigns the generated ID to the worker object.
     *
     * @param worker the {@link Worker} object to be added
     * @return {@code true} if the insertion was successful, otherwise {@code false}
     */
    public boolean addWorker(Worker worker) {
        String sql = """
        INSERT INTO worker (
            username, password, fullName, email, phoneNumber,
            dateOfBirth, gender, hireDate, status, salary,
            specialization, enclosure
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, worker.getUsername());
            pstmt.setString(2, worker.getPassword());
            pstmt.setString(3, worker.getFullName());
            pstmt.setString(4, worker.getEmail());
            pstmt.setString(5, worker.getPhoneNumber());
            pstmt.setDate(6, Date.valueOf(worker.getDateOfBirth()));
            pstmt.setString(7, worker.getGender());
            pstmt.setDate(8, Date.valueOf(worker.getHireDate()));
            pstmt.setString(9, worker.getStatus().name());
            pstmt.setInt(10, worker.getSalary());
            pstmt.setString(11, worker.getSpecialization().name());
            pstmt.setLong(12, worker.getEnclosure().getId());

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    worker.setId(keys.getLong(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
