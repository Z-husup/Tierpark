package com.prog.tierpark.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditWorkerController {

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
        // TODO: Optional: setup ComboBox items or disable fields
    }

    @FXML
    private void handleEditingMode() {
        System.out.println("Editing mode toggled");
        // TODO: Add logic to enable editing of text fields and combo boxes
    }

    @FXML
    private void handeChangeDetails() {
        System.out.println("Change details submitted");
        // TODO: Add logic to save updated details
    }

}
