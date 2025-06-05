package com.prog.tierpark.service;

import java.sql.Date;
import java.util.List;

import com.prog.tierpark.model.Ticket;
import com.prog.tierpark.model.enums.TicketStatus;
import com.prog.tierpark.repository.TicketRepository;

/**
 * Service zur Verwaltung von Tickets.
 * Stellt Methoden zum Hinzufügen, Abrufen und Ändern von Tickets bereit
 * und kommuniziert mit dem Ticket-Repository.
 */
public class TicketService {
    TicketRepository ticketRepository = new TicketRepository();

    /**
     * Fügt ein neues Ticket hinzu.
     *
     * @param ticket Das hinzuzufügende Ticket-Objekt
     * @return Das hinzugefügte Ticket mit der vergebenen ID
     */
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.saveTicket(ticket);
    }

    /**
     * Gibt eine Liste aller Tickets zurück.
     *
     * @return Eine Liste aller Tickets
     */
    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

    /**
     * Sucht ein Ticket anhand der eindeutigen ID.
     *
     * @param id Die eindeutige ID des Tickets
     * @return Das Ticket-Objekt, falls gefunden, sonst null
     */
    public Ticket getTicketById(long id) {
        return ticketRepository.getTicketById(id);
    }

    /**
     * Speichert ein Ticket in der Datenbank.
     * Kann zum Hinzufügen eines neuen Tickets verwendet werden.
     *
     * @param ticket Das zu speichernde Ticket
     */
    public void saveTicket(Ticket ticket) {
        ticketRepository.saveTicket(ticket);
    }

    /**
     * Aktualisiert ein bestehendes Ticket in der Datenbank.
     * Es wird nach der ID gesucht, und die relevanten Felder werden überschrieben.
     *
     * @param ticket Das Ticket mit den aktualisierten Daten
     */
    public void changeTicket(Ticket ticket) {
        Ticket updatedTicket = getTicketById(ticket.getId());
        updatedTicket.setPrice(ticket.getPrice());
        updatedTicket.setDay(ticket.getDay());
        updatedTicket.setStatus(ticket.getStatus());
        updatedTicket.setType(ticket.getType());
        ticketRepository.updateTicket(updatedTicket);
    }

    /**
     * Gibt eine Liste aller Tickets zurück, deren Kaufdatum
     * innerhalb des angegebenen Datumsbereichs liegt (einschließlich).
     *
     * @param start Startdatum (inklusive)
     * @param end   Enddatum (inklusive)
     * @return Eine Liste gefilterter Tickets nach Datum
     */
    public List<Ticket> getTicketsBetweenDates(Date start, Date end) {
        return ticketRepository.getTicketsByDateRange(start, end);
    }

    /**
     * Berechnet die Gesamteinnahmen aus einer Liste von Tickets,
     * wobei Tickets mit dem Status "refunded" ausgeschlossen werden.
     *
     * @param tickets Liste der zu analysierenden Tickets
     * @return Gesamtsumme der Preise nicht zurückerstatteter Tickets
     */
    public double getTotalRevenueExcludingRefunded(List<Ticket> tickets) {
        return tickets.stream()
                .filter(ticket -> ticket.getStatus() != TicketStatus.refunded)
                .mapToDouble(Ticket::getPrice)
                .sum();
    }
}
