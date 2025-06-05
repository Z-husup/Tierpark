package com.prog.tierpark.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Ticket;
import com.prog.tierpark.model.enums.TicketStatus;
import com.prog.tierpark.model.enums.TicketType;

/**
 * Repository-Klasse für den Zugriff auf die Ticket-Datenbank.
 * Stellt Methoden bereit, um Tickets aus der Datenbank abzurufen, zu speichern und zu aktualisieren.
 */
public class TicketRepository {

    /**
     * Liefert eine Liste aller Tickets aus der Datenbank zurück.
     *
     * @return Liste aller Tickets (aktuell leer, Implementierung fehlt)
     */
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        // TODO: Implementierung zum Abrufen aller Tickets aus der DB hinzufügen
        return tickets;
    }

    /**
     * Sucht ein Ticket anhand seiner eindeutigen ID.
     *
     * @param id Die ID des Tickets
     * @return Das Ticket mit der angegebenen ID oder null, wenn nicht gefunden
     */
    public Ticket getTicketById(long id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ticket(
                        rs.getLong("id"),
                        TicketType.valueOf(rs.getString("type")),
                        rs.getDate("theDAte"),
                        rs.getDouble("price"),
                        TicketStatus.valueOf(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Speichert ein neues Ticket in der Datenbank.
     * Setzt die generierte ID im Ticket-Objekt nach erfolgreichem Speichern.
     *
     * @param ticket Das Ticket, das gespeichert werden soll
     * @return Das gespeicherte Ticket mit der gesetzten ID oder null bei Fehlern
     */
    public Ticket saveTicket(Ticket ticket) {
        String sql = "INSERT INTO ticket (type, theDate, price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, ticket.getType().name());
            pstmt.setDate(2, new java.sql.Date(ticket.getDay().getTime()));
            pstmt.setDouble(3, ticket.getPrice());
            pstmt.setString(4, ticket.getStatus().name());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    ticket.setId(id);
                    return ticket;
                } else {
                    throw new SQLException("Speichern des Tickets fehlgeschlagen: keine ID erhalten.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aktualisiert ein bestehendes Ticket in der Datenbank.
     * Aktuelle Implementierung fehlt.
     *
     * @param ticket Das Ticket mit aktualisierten Werten
     */
    public void updateTicket(Ticket ticket) {
        String sql = "UPDATE ticket SET type = ?, theDate = ?, price = ?, status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DatabaseManager.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticket.getType().name());
            pstmt.setDate(2, new java.sql.Date(ticket.getDay().getTime()));
            pstmt.setDouble(3, ticket.getPrice());
            pstmt.setString(4, ticket.getStatus().name());
            pstmt.setLong(5, ticket.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
