package com.prog.tierpark.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                        rs.getString("name"),
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

}

