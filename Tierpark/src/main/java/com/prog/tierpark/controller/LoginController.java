package com.prog.tierpark.controller;

import com.prog.tierpark.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passportField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {

        roleComboBox.getItems().addAll("ADMIN", "WORKER");

    }

    @FXML
    private void handleLogin() {
        System.out.println("Login button clicked");
        String username = usernameField.getText();
        String password = passportField.getText();
        String role = roleComboBox.getValue();

        // TODO: Add actual authentication logic
        if (role.equals("ADMIN")) {
            Application.switchScene("admin-menu-view.fxml");
        }
        if (role.equals("WORKER")) {
            Application.switchScene("worker-menu-view.fxml");
        }
        else System.out.printf("Attempt login: %s / %s as %s%n", username, password, role);

    }
}
