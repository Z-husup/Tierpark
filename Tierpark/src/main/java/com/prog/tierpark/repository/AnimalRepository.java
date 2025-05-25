package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.AnimalStatus;
import com.prog.tierpark.model.Species;
import com.prog.tierpark.model.enums.AnimalGender;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    public List<Animal> findAll() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animal";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Animal animal = new Animal();
                animal.setId(rs.getLong("id"));
                animal.setName(rs.getString("name"));

                Species species = new Species();
                species.setName(rs.getString("species"));
                animal.setSpecies(species);

                animal.setDateOfBirth(LocalDate.parse(rs.getString("date_of_birth")));
                animal.setArrivalDate(LocalDate.parse(rs.getString("arrival_date")));
                animal.setAge(rs.getInt("age"));

                animal.setGender(AnimalGender.valueOf(rs.getString("gender")));
                animal.setSize(rs.getInt("size"));
                animal.setWeight(rs.getInt("weight"));

                AnimalStatus status = new AnimalStatus(
                        rs.getString("health_status"),
                        rs.getString("breeding_status"),
                        rs.getInt("is_dead") == 1
                );
                animal.setStatus(status);

                animals.add(animal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }


    public void save(Animal animal) {
        String sql = "INSERT INTO animal(name, species, date_of_birth, arrival_date, age, gender, size, weight, health_status, breeding_status, is_dead) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, animal.getName());
            stmt.setString(2, animal.getSpecies().getName()); // ← берём имя из вложенного класса
            stmt.setString(3, animal.getDateOfBirth().toString());
            stmt.setString(4, animal.getArrivalDate().toString());
            stmt.setInt(5, animal.getAge());
            stmt.setString(6, animal.getGender().name());
            stmt.setInt(7, animal.getSize());
            stmt.setInt(8, animal.getWeight());
            stmt.setString(9, animal.getStatus().getHealthStatus());
            stmt.setString(10, animal.getStatus().getBreedingStatus());
            stmt.setInt(11, animal.getStatus().isDead() ? 1 : 0);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
