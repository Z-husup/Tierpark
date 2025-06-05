package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.MedicalHistory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryRepository {

    /**
     * Retrieves all medical history records for a given animal.
     *
     * @param animalId The ID of the animal.
     * @return List of {@link MedicalHistory} entries.
     */
    public List<MedicalHistory> getMedicalRecordsByAnimalId(long animalId) {
        List<MedicalHistory> records = new ArrayList<>();
        String sql = "SELECT * FROM medicalhistory WHERE animalId = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, animalId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                records.add(new MedicalHistory(
                        rs.getInt("id"),
                        rs.getInt("animalId"),
                        rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null,
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    /**
     * Inserts a new medical history record for an animal.
     *
     * @param animalId    The animal's ID.
     * @param description The description of the treatment or observation.
     * @return {@code true} if inserted successfully.
     */
    public boolean addMedicalRecord(long animalId, String description) {
        String sql = "INSERT INTO medicalhistory (animalId, date, description) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, animalId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setString(3, description);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a medical history entry by ID.
     *
     * @param id The ID of the medical history record.
     * @return {@code true} if deletion was successful.
     */
    public boolean deleteMedicalRecord(long id) {
        String sql = "DELETE FROM medicalhistory WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

