package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.AnimalStatus;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Species;
import com.prog.tierpark.model.enums.AnimalGender;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for accessing animal records along with related species, status, and enclosure data.
 */
public class AnimalRepository {

    /**
     * Retrieves all animals including their species, status, and enclosure data.
     *
     * @return List of {@link Animal} objects.
     */
    public List<Animal> getAllAnimals() {
        List<Animal> list = new ArrayList<>();

        String sql = """
            SELECT 
                animal.id              AS animal_id,
                animal.name            AS animal_name,
                animal.dateOfBirth     AS animal_birth_date,
                animal.arrivalDate     AS animal_arrival_date,
                animal.age             AS animal_age,
                animal.gender          AS animal_gender,
                animal.size            AS animal_size,
                animal.weight          AS animal_weight,

                species.id             AS species_id,
                species.name           AS species_name,
                species.habitat        AS species_habitat,
                species.animalClass    AS species_class,
                species.diet           AS species_diet,

                status.id              AS status_id,
                status.healthStatus    AS status_health,
                status.breedingStatus  AS status_breeding,
                status.isDead          AS status_is_dead,

                enclosure.id           AS enclosure_id,
                enclosure.name         AS enclosure_name,
                enclosure.zone         AS enclosure_zone,
                enclosure.status       AS enclosure_status,
                enclosure.type         AS enclosure_type,
                enclosure.capacity     AS enclosure_capacity,
                enclosure.description  AS enclosure_description,
                enclosure.condition    AS enclosure_condition,
                enclosure.area         AS enclosure_area
            FROM animal
            LEFT JOIN species      ON animal.species   = species.id
            LEFT JOIN animalstatus AS status ON animal.status    = status.id
            LEFT JOIN enclosure    ON animal.enclosure = enclosure.id
        """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractAnimal(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieves a single animal by its ID.
     *
     * @param id The ID of the animal.
     * @return The {@link Animal} object, or null if not found.
     */
    public Animal getAnimalById(long id) {
        String sql = """
            SELECT 
                animal.id              AS animal_id,
                animal.name            AS animal_name,
                animal.dateOfBirth     AS animal_birth_date,
                animal.arrivalDate     AS animal_arrival_date,
                animal.age             AS animal_age,
                animal.gender          AS animal_gender,
                animal.size            AS animal_size,
                animal.weight          AS animal_weight,

                species.id             AS species_id,
                species.name           AS species_name,
                species.habitat        AS species_habitat,
                species.animalClass    AS species_class,
                species.diet           AS species_diet,

                status.id              AS status_id,
                status.healthStatus    AS status_health,
                status.breedingStatus  AS status_breeding,
                status.isDead          AS status_is_dead,

                enclosure.id           AS enclosure_id,
                enclosure.name         AS enclosure_name,
                enclosure.zone         AS enclosure_zone,
                enclosure.status       AS enclosure_status,
                enclosure.type         AS enclosure_type,
                enclosure.capacity     AS enclosure_capacity,
                enclosure.description  AS enclosure_description,
                enclosure.condition    AS enclosure_condition,
                enclosure.area         AS enclosure_area
            FROM animal
            LEFT JOIN species      ON animal.species   = species.id
            LEFT JOIN animalstatus AS status ON animal.status    = status.id
            LEFT JOIN enclosure    ON animal.enclosure = enclosure.id
            WHERE animal.id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractAnimal(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Extracts an {@link Animal} from the current row of the given {@link ResultSet}.
     *
     * @param rs ResultSet positioned at the current row.
     * @return Constructed {@link Animal} object.
     * @throws SQLException If column access fails.
     */
    private Animal extractAnimal(ResultSet rs) throws SQLException {
        Species species = new Species(
                rs.getLong("species_id"),
                rs.getString("species_name"),
                rs.getString("species_habitat"),
                rs.getString("species_class"),
                rs.getString("species_diet")
        );

        AnimalStatus status = new AnimalStatus(
                rs.getLong("status_id"),
                rs.getString("status_health"),
                rs.getString("status_breeding"),
                rs.getBoolean("status_is_dead")
        );

        Enclosure enclosure = new Enclosure(
                rs.getLong("enclosure_id"),
                rs.getString("enclosure_name"),
                rs.getString("enclosure_zone"),
                rs.getString("enclosure_status"),
                rs.getString("enclosure_type"),
                rs.getInt("enclosure_capacity"),
                rs.getString("enclosure_description"),
                rs.getString("enclosure_condition"),
                rs.getString("enclosure_area")
        );

        return new Animal(
                rs.getLong("animal_id"),
                rs.getString("animal_name"),
                species,
                getNullableDate(rs, "animal_birth_date"),
                getNullableDate(rs, "animal_arrival_date"),
                rs.getInt("animal_age"),
                AnimalGender.valueOf(rs.getString("animal_gender").toUpperCase()),
                rs.getInt("animal_size"),
                rs.getInt("animal_weight"),
                status,
                enclosure
        );
    }

    /**
     * Converts an SQL date to a {@link LocalDate} or returns null.
     *
     * @param rs     The result set.
     * @param column The column name.
     * @return A {@link LocalDate} or {@code null}.
     * @throws SQLException If column reading fails.
     */
    private LocalDate getNullableDate(ResultSet rs, String column) throws SQLException {
        Date date = rs.getDate(column);
        return date != null ? date.toLocalDate() : null;
    }
}

