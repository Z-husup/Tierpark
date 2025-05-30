package com.prog.tierpark.repository;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.model.enums.ScheduleType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository {

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
}
