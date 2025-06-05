package com.prog.tierpark.controller;

import com.prog.tierpark.Application;

import javafx.fxml.FXML;

/**
 * Controller for the application's start page.
 * Handles navigation to login and ticket-related pages.
 */
public class StartPageController {

    /**
     * Navigates to the login page for administrators.
     * Currently uses the same login view as workers.
     */
    @FXML
    protected void toAdminPage() {
        Application.switchScene("login-view.fxml");
    }

    /**
     * Navigates to the login page for workers.
     * Currently uses the same login view as administrators.
     */
    @FXML
    protected void toWorkerPage() {
        Application.switchScene("login-view.fxml");
    }

    /**
     * Navigates to the public ticket page where guests can view or purchase tickets.
     */
    @FXML
    protected void toTicketPage() {
        Application.switchScene("ticket-page-view.fxml");
    }
}
