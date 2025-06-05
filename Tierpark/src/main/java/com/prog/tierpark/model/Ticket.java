package com.prog.tierpark.model;

import java.util.Date;

import com.prog.tierpark.model.enums.TicketStatus;
import com.prog.tierpark.model.enums.TicketType;

import lombok.Data;

/**
 * Modellklasse für ein Ticket im Tierpark-System.
 * <p>
 * Ein Ticket besitzt eine eindeutige ID, einen Tickettyp, ein Datum, einen Preis und einen Status.
 * Der Preis wird automatisch anhand des Tickettyps festgelegt.
 * </p>
 */
@Data
public class Ticket {
    /**
     * Eindeutige Identifikationsnummer des Tickets.
     */
    long id;

    /**
     * Typ des Tickets (z.B. Kind, Student, Erwachsener, Senior).
     */
    TicketType type;

    /**
     * Gültigkeitsdatum des Tickets.
     */
    Date day;

    /**
     * Preis des Tickets in Euro.
     */
    double price;

    /**
     * Status des Tickets (z.B. aktiv, storniert).
     */
    TicketStatus status = TicketStatus.active;

    /**
     * Konstruktor zur Erstellung eines neuen Tickets mit Typ und Datum.
     * Der Preis wird abhängig vom Tickettyp gesetzt.
     *
     * @param type Der Typ des Tickets (child, student, adult, senior)
     * @param day  Das Gültigkeitsdatum des Tickets
     */
    public Ticket(TicketType type, Date day) {
        this.type = type;
        this.day = day;
        switch (type) {
            case child:
                this.price = 5.0;
                break;
            case student:
                this.price = 7.0;
                break;
            case adult:
                this.price = 10.0;
                break;
            case senior:
                this.price = 8.0;
                break;
            default:
                break;
        }
    }

    /**
     * Konstruktor zur Erstellung eines vollständigen Tickets mit allen Eigenschaften.
     *
     * @param id     Eindeutige ID des Tickets
     * @param type   Typ des Tickets
     * @param day    Gültigkeitsdatum des Tickets
     * @param price  Preis des Tickets
     * @param status Status des Tickets (z.B. aktiv, storniert)
     */
    public Ticket(long id, TicketType type, Date day, double price, TicketStatus status) {
        this.id = id;
        this.type = type;
        this.day = day;
        this.price = price;
        this.status = status;
    }
}
