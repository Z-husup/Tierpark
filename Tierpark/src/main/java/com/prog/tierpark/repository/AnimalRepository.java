package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.enums.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    public boolean addAnimal(Animal animal) {
        String sql = """
            INSERT INTO animal (name, animalGroup, dateOfBirth, arrivalDate, age, gender, size, weight, healthStatus, enclosure)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, animal.getName());
            pstmt.setString(2, animal.getAnimalGroup().name());
            pstmt.setDate(3, Date.valueOf(animal.getDateOfBirth()));
            pstmt.setDate(4, Date.valueOf(animal.getArrivalDate()));
            pstmt.setInt(5, animal.getAge());
            pstmt.setString(6, animal.getGender().name());
            pstmt.setInt(7, animal.getSize());
            pstmt.setInt(8, animal.getWeight());
            pstmt.setString(9, animal.getHealthStatus().name());
            pstmt.setLong(10, animal.getEnclosure().getId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    animal.setId(keys.getLong(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Animal> getAnimalsByEnclosureId(long enclosureId) {
        List<Animal> list = new ArrayList<>();
        String sql = "SELECT * FROM animal WHERE enclosure = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, enclosureId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Animal animal = new Animal(
                        rs.getLong("id"),
                        rs.getString("name"),
                        AnimalGroup.valueOf(rs.getString("animalGroup")),
                        rs.getDate("dateOfBirth").toLocalDate(),
                        rs.getDate("arrivalDate").toLocalDate(),
                        rs.getInt("age"),
                        AnimalGender.valueOf(rs.getString("gender")),
                        rs.getInt("size"),
                        rs.getInt("weight"),
                        HealthStatus.valueOf(rs.getString("healthStatus")),
                        null, // optional: load MedicalHistory separately
                        null  // optional: Enclosure already known
                );
                list.add(animal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}


