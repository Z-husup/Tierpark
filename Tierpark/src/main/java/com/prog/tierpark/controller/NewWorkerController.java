package com.prog.tierpark.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class NewWorkerController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField numberField;

    @FXML
    private DatePicker dateOfBirthField;

    @FXML
    private TextField genderField;

    @FXML
    private ComboBox enclosureCombo;

    @FXML
    private ComboBox statusCombo;

    @FXML
    private TextField salaryField;

    @FXML
    public void initialize() {

        statusCombo.getItems().addAll("WORKING", "VACATION", "RETIRED");

    }

    @FXML
    private void handleAddNewWorker() {
        System.out.println("Adding new worker...");

        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = numberField.getText();
        String dob = dateOfBirthField.getValue() != null ? dateOfBirthField.getValue().toString() : "";
        String gender = genderField.getText();
        String enclosure = enclosureCombo.getValue() != null ? enclosureCombo.getValue().toString() : "";
        String status = statusCombo.getValue() != null ? statusCombo.getValue().toString() : "";
        String salary = salaryField.getText();

        // TODO: Add validation and persist logic
    }
}
