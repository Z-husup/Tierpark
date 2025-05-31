package com.prog.tierpark.controller;

import com.prog.tierpark.Application;
import com.prog.tierpark.Session;
import com.prog.tierpark.model.Admin;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.service.AdminService;
import com.prog.tierpark.service.WorkerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller responsible for handling user login via the JavaFX interface.
 * It supports login for two roles: ADMIN and WORKER.
 */
public class LoginController {

    /** Text field for entering the username */
    @FXML
    private TextField usernameField;

    /** Text field for entering the password */
    @FXML
    private TextField passportField;

    /** Combo box for selecting the user role (ADMIN or WORKER) */
    @FXML
    private ComboBox<String> roleComboBox;

    /**
     * Initializes the login screen components.
     * This method is automatically called after FXML fields are injected.
     * Populates the role selection combo box.
     */
    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("ADMIN", "WORKER");
    }

    /**
     * Handles login logic when the login button is clicked.
     * Checks credentials based on the selected role and navigates to the appropriate menu view.
     * If login fails, prints an error message.
     */
    @FXML
    private void handleLogin() {
        System.out.println("Login button clicked");
        String username = usernameField.getText();
        String password = passportField.getText();
        String role = roleComboBox.getValue();

        if (role.equals("ADMIN")) {
            AdminService service = new AdminService();
            Admin admin = service.login(username, password);

            if (admin != null) {
                System.out.println("✅ Login successful. Welcome " + admin.getUsername());
                Application.switchScene("admin-menu-view.fxml");
            } else {
                System.out.println("❌ Invalid credentials.");
            }
        }

        if (role.equals("WORKER")) {
            WorkerService service = new WorkerService();
            Worker logged = service.login(username, password);
            if (logged != null) {
                System.out.println("✅ Login successful: " + logged.getFullName());

                Session.setLoggedInWorker(logged);
                Application.switchScene("worker-menu-view.fxml");


            } else {
                System.out.println("❌ Invalid credentials.");
            }
        }


    }
}

