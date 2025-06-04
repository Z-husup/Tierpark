package com.prog.tierpark.controller;

import com.prog.tierpark.Application;

import javafx.fxml.FXML;

public class StartPageController {

    @FXML
    protected void toAdminPage() {
        Application.switchScene("login-view.fxml");
    }

    @FXML
    protected void toWorkerPage() {
        Application.switchScene("login-view.fxml");
    }

    @FXML
    protected void toTicketPage() {
        Application.switchScene("ticket-view.fxml");
    }
}