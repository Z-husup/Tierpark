package com.prog.tierpark.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.model.enums.ScheduleType;

/**
 * Repository class responsible for accessing schedule data from the database.
 */
public class ScheduleRepository {

    /**
     * Retrieves a list of schedules assigned to a specific enclosure.
     *
     * @param enclosureId the ID of the enclosure whose schedules should be fetched.
     * @return a list of {@link Schedule} objects associated with the given enclosure.
     */
    public List<Schedule> getSchedulesByEnclosureId(long enclosureId) {
        List<Schedule> list = new ArrayList<>();
        String sql = "SELECT * FROM schedule WHERE enclosure = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, enclosureId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Schedule(
                        rs.getLong("id"),
                        rs.getString("description"),
                        ScheduleType.valueOf(rs.getString("scheduleType")),
                        rs.getDate("startingTime").toLocalDate().atStartOfDay()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Deletes a schedule from the database by its ID.
     *
     * @param id the ID of the schedule to delete.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     */
    public boolean deleteSchedule(long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";

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
     * Inserts a new schedule into the database for the given enclosure.
     *
     * @param schedule    The {@link Schedule} object to insert.
     * @param enclosureId The ID of the enclosure to associate the schedule with.
     * @return {@code true} if the insert was successful, {@code false} otherwise.
     */
    public boolean addSchedule(Schedule schedule, long enclosureId) {
        String sql = """
        INSERT INTO schedule (description, scheduleType, startingTime, enclosure)
        VALUES (?, ?, ?, ?)
    """;

        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, schedule.getDescription());
            pstmt.setString(2, schedule.getScheduleType().name());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(schedule.getStartingTime()));
            pstmt.setLong(4, enclosureId);

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    schedule.setId(keys.getLong(1)); // optional: store generated ID
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}

