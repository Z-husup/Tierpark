package com.prog.tierpark.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.prog.tierpark.model.Ticket;
import com.prog.tierpark.model.enums.TicketStatus;
import com.prog.tierpark.model.enums.TicketType;
import com.prog.tierpark.service.TicketService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller-Klasse für die Verwaltung von Ticket-Interaktionen in der GUI.
 * <p>
 * Verantwortlich für das Kaufen und Zurückerstatten von Tickets sowie die
 * Initialisierung der GUI-Komponenten wie ComboBox und DatePicker.
 * </p>
 */
public class TicketController {
    private final TicketService ticketService = new TicketService();

    @FXML
    private ComboBox<TicketType> groupComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField ticketIdField;

    @FXML
    private Label statusLabel;

    /**
     * Initialisiert die GUI-Komponenten bei Laden des Controllers.
     * Füllt die ComboBox mit den verfügbaren Tickettypen und setzt das Statuslabel zurück.
     */
    @FXML
    private void initialize() {
        groupComboBox.getItems().addAll(TicketType.values());
        groupComboBox.setPromptText("Wähle Tickettyp");
        if (statusLabel != null) {
            statusLabel.setText("");
        }
    }

    /**
     * Event-Handler für den Kauf eines Tickets.
     * Liest die Eingaben aus der GUI aus, erstellt ein neues Ticket und speichert es.
     * Setzt eine Statusmeldung mit Preis und Ticket-ID oder Fehlerhinweis.
     *
     * @param event das ActionEvent, ausgelöst durch das Klicken des Kauf-Buttons
     */
    @FXML
    private void handleBuyTicket(ActionEvent event) {
        TicketType selectedType = groupComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedType == null) {
            statusLabel.setText("Bitte Tickettyp auswählen.");
            return;
        }
        if (selectedDate == null) {
            statusLabel.setText("Bitte Datum auswählen.");
            return;
        }

        try {
            Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Ticket newTicket = new Ticket(selectedType, date);
            Ticket savedTicket = ticketService.addTicket(newTicket);

            if (savedTicket != null) {
                statusLabel.setText("Ticket gekauft! Preis: " + savedTicket.getPrice() +
                        " €, Ticket-ID: " + savedTicket.getId());
            } else {
                statusLabel.setText("Fehler beim Speichern des Tickets.");
            }
        } catch (Exception e) {
            statusLabel.setText("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    /**
     * Event-Handler für die Rückerstattung eines Tickets.
     * Liest die Ticket-ID aus und zeigt eine Bestätigung oder Fehlermeldung an.
     *
     * @param event das ActionEvent, ausgelöst durch das Klicken des Rückerstattungs-Buttons
     */
    @FXML
    private void handleRefund(ActionEvent event) {
        String ticketId = ticketIdField.getText();

        if (ticketId == null || ticketId.isEmpty()) {
            statusLabel.setText("Bitte gültige Ticket-ID eingeben.");
            return;
        }
        Ticket newTicket = ticketService.getTicketById(Long.parseLong(ticketId));
        if (newTicket == null) {statusLabel.setText("Kein Ticket gefunden.");}
        newTicket.setStatus(TicketStatus.refunded);
        ticketService.changeTicket(newTicket);
        statusLabel.setText("Ticket mit ID " + ticketId + " wurde zurückerstattet.");
    }
}
