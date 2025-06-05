package com.prog.tierpark.controller;

import com.prog.tierpark.Application;
import com.prog.tierpark.model.Ticket;
import com.prog.tierpark.model.enums.TicketStatus;
import com.prog.tierpark.model.enums.TicketType;
import com.prog.tierpark.repository.TicketRepository;
import com.prog.tierpark.service.TicketService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Controller für die Admin-Ansicht von Tickets im Tierpark-System.
 * Bietet Funktionen zum Filtern von Tickets nach Datum und zur Anzeige
 * der Gesamteinnahmen ohne zurückerstattete Tickets.
 */
public class TicketAdminController {

    private final TicketService ticketService = new TicketService();
    private final TicketRepository ticketRepository = new TicketRepository();

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, Long> idColumn;

    @FXML
    private TableColumn<Ticket, TicketType> typeColumn;

    @FXML
    private TableColumn<Ticket, Date> dateColumn;

    @FXML
    private TableColumn<Ticket, Double> priceColumn;

    @FXML
    private TableColumn<Ticket, TicketStatus> statusColumn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label revenueLabel;

    /**
     * Initialisiert die Admin-Oberfläche, lädt alle Tickets aus der Datenbank,
     * konfiguriert die Tabellenspalten und berechnet die anfängliche Gesamteinnahme.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getId()).asObject());
        typeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getType()));
        dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDay()));
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        statusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus()));

        List<Ticket> tickets = ticketRepository.getAllTickets();
        ticketTable.setItems(FXCollections.observableArrayList(tickets));
        updateRevenueLabel(tickets);
    }

    /**
     * Navigiert zurück zur Startseite der Anwendung.
     *
     * @param event das auslösende Event, typischerweise ein Button-Klick
     */
    @FXML
    private void goToStartPage(ActionEvent event) {
        Application.switchScene("start-view.fxml");
    }

    /**
     * Filtert die Tickets in der Tabelle anhand des gewählten Datumsbereichs.
     * Zeigt nur Tickets an, deren Kaufdatum zwischen Start- und Enddatum liegt.
     * Aktualisiert zusätzlich die Gesamteinnahmeanzeige.
     *
     * @param event das auslösende Event, typischerweise ein Button-Klick
     */
    @FXML
    private void handleDateFilter(ActionEvent event) {
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start != null && end != null && !end.isBefore(start)) {
            java.sql.Date sqlStart = java.sql.Date.valueOf(start);
            java.sql.Date sqlEnd = java.sql.Date.valueOf(end);

            List<Ticket> filtered = ticketService.getTicketsBetweenDates(sqlStart, sqlEnd);
            ticketTable.setItems(FXCollections.observableArrayList(filtered));
            updateRevenueLabel(filtered);
        } else {
            showAlert("Ungültiger Datumsbereich: Wählen Sie Start- und Enddatum korrekt aus.");
        }
    }

    /**
     * Zeigt eine Warnmeldung in einem Alert-Dialog an.
     *
     * @param message Die Nachricht, die dem Benutzer angezeigt werden soll
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Date Filter Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Berechnet die Gesamteinnahmen aller übergebenen Tickets,
     * ohne zurückerstattete Tickets, und zeigt diese im Label an.
     *
     * @param tickets Die Liste der anzuzeigenden Tickets
     */
    private void updateRevenueLabel(List<Ticket> tickets) {
        double total = ticketService.getTotalRevenueExcludingRefunded(tickets);
        revenueLabel.setText(String.format("Gesamteinnahmen (ohne Rückerstattung): %.2f €", total));
    }
//    @FXML
//    private void goToStartPage(ActionEvent event) {
//        Application.switchScene("start-view.fxml");
//    }
}
