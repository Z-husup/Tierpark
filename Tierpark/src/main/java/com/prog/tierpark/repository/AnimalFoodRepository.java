package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.AnimalFood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing AnimalFood data in the database.
 * Provides methods for inserting, updating, and retrieving animal food records.
 */
public class AnimalFoodRepository {

    /**
     * Adds a new {@link AnimalFood} entry to the database.
     *
     * @param food the AnimalFood object to insert
     * @return true if insertion was successful, false otherwise
     */
    public boolean addFood(AnimalFood food) {
        String sql = """
            INSERT INTO animalfood (name, quantity, weight, deliveryDate, expirationDate, storageCondition)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, food.getName());
            pstmt.setInt(2, food.getQuantity());
            pstmt.setInt(3, food.getWeight());
            pstmt.setDate(4, Date.valueOf(food.getDeliveryDate()));
            pstmt.setDate(5, Date.valueOf(food.getExpirationDate()));
            pstmt.setString(6, food.getStorageCondition());

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    food.setId(keys.getLong(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all {@link AnimalFood} records from the database.
     *
     * @return list of AnimalFood objects
     */
    public List<AnimalFood> getAllFood() {
        List<AnimalFood> list = new ArrayList<>();
        String sql = "SELECT * FROM animalfood";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AnimalFood food = new AnimalFood(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getInt("weight"),
                        rs.getDate("deliveryDate").toLocalDate(),
                        rs.getDate("expirationDate").toLocalDate(),
                        rs.getString("storageCondition")
                );
                list.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Updates an existing {@link AnimalFood} record in the database.
     *
     * @param food the AnimalFood object with updated values
     * @return true if update was successful, false otherwise
     */
    public boolean updateFood(AnimalFood food) {
        String sql = """
            UPDATE animalfood SET name=?, quantity=?, weight=?, deliveryDate=?, expirationDate=?, storageCondition=?
            WHERE id=?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, food.getName());
            pstmt.setInt(2, food.getQuantity());
            pstmt.setInt(3, food.getWeight());
            pstmt.setDate(4, Date.valueOf(food.getDeliveryDate()));
            pstmt.setDate(5, Date.valueOf(food.getExpirationDate()));
            pstmt.setString(6, food.getStorageCondition());
            pstmt.setLong(7, food.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

