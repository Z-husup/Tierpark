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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TicketAdminController {
    TicketService ticketService=new TicketService();
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

    private final TicketRepository ticketRepository = new TicketRepository();

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getId()).asObject());
        typeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getType()));
        dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDay()));
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        statusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus()));

        ticketTable.setItems(FXCollections.observableArrayList(ticketRepository.getAllTickets()));
    }

    @FXML
    private void goToStartPage(ActionEvent event) {
        Application.switchScene("start-view.fxml");
    }

    @FXML
    private void handleDateFilter(ActionEvent event) {
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start != null && end != null && !end.isBefore(start)) {
            java.sql.Date sqlStart = java.sql.Date.valueOf(start);
            java.sql.Date sqlEnd = java.sql.Date.valueOf(end);

            List<Ticket> filtered = ticketService.getTicketsBetweenDates(sqlStart, sqlEnd);
            ticketTable.setItems(FXCollections.observableArrayList(filtered));
        } else {
            showAlert("Ungültiger Datumsbereich: Wählen Sie Start- und Enddatum korrekt aus.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Date Filter Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
