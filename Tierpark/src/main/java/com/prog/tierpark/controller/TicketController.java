package com.prog.tierpark.controller;

import java.util.List;

import com.prog.tierpark.model.Ticket;
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
    private void handleBuyTicket(ActionEvent event) {
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

    @FXML
    private void handleRefund (ActionEvent event) {
        String name = nameField.getText();
        String ticketCountText = ticketCountField.getText();

        try {
            int ticketCount = Integer.parseInt(ticketCountText);
            if (ticketCount <= 0) {
                statusLabel.setText("Введите положительное число билетов для возврата.");
            } else {
                // Тут может быть логика возврата билета
                statusLabel.setText("Билеты возвращены: " + ticketCount + " шт. для " + name);
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Введите корректное число билетов для возврата.");
        }
    }

    @FXML
    private void getTicketAsQr(){
        
    }

    @FXML
    private List<Ticket>  getAllTickets(ActionEvent event) {
        // Здесь должна быть логика получения всех билетов
        statusLabel.setText("Получены все билеты.");
        return ticketService.getAllTickets();
    }

    
}
