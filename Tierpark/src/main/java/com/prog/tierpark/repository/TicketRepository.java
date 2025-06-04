package com.prog.tierpark.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Ticket;
import com.prog.tierpark.model.enums.TicketStatus;
import com.prog.tierpark.model.enums.TicketType;

public class TicketRepository {
    public List<Ticket> getAllTickets() {
        List <Ticket> tickets = new ArrayList<>();

        return tickets;
    }
    public Ticket getTicketById(long id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                // return new Ticket(
                //         rs.getString("type"),
                //         rs.getDate("day")
                // );
                return new Ticket(
                        rs.getLong("id"),
                        TicketType.valueOf(rs.getString("type")),
                        rs.getDate("day"),
                        rs.getDouble("price"),
                        TicketStatus.valueOf(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        return null;
        }
        return null;
    }
    public void saveTicket(Ticket ticket) {
        String sql = "INSERT INTO ticket (type, day, price, status) VALUES (?, ?, ?, ?)";
        try(Connection conn = DriverManager.getConnection(DatabaseManager.URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticket.getType().name());
            pstmt.setDate(2, new java.sql.Date(ticket.getDay().getTime()));
            pstmt.setDouble(3, ticket.getPrice());
            pstmt.setString(4, ticket.getStatus().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void updateTicket(Ticket ticket) {
        // Здесь должна быть логика обновления билета в базе данных
    }
}
