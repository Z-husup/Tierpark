package com.prog.tierpark.controller;

import com.prog.tierpark.service.TicketService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TicketController {
    TicketService ticketService;
        @FXML
    private TextField nameField;

    @FXML
    private TextField ticketCountField;

    @FXML
    private Button purchaseButton;

    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        // Инициализация контроллера после загрузки FXML
        statusLabel.setText("");
    }

    @FXML
    private void handlePurchaseButton(ActionEvent event) {
        String name = nameField.getText();
        String ticketCountText = ticketCountField.getText();

        try {
            int ticketCount = Integer.parseInt(ticketCountText);
            if (ticketCount <= 0) {
                statusLabel.setText("Введите положительное число билетов.");
            } else {
                // Тут может быть логика покупки билета
                statusLabel.setText("Билеты куплены: " + ticketCount + " шт. для " + name);
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Введите корректное число билетов.");
        }
    }

}
