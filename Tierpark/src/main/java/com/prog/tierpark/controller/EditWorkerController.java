package com.prog.tierpark.controller;

import com.prog.tierpark.database.DatabaseManager;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.model.enums.WorkerStatus;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.WorkerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EditWorkerController {

    private Worker worker;

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

        statusCombo.getItems().addAll(WorkerStatus.values());

        EnclosureRepository enclosureRepo = new EnclosureRepository();
        List<Enclosure> enclosures = enclosureRepo.getAllEnclosures();
        enclosureCombo.getItems().addAll(enclosures);

    }

    @FXML
    private void handleEditingMode() {
        System.out.println("🔓 Editing mode enabled");
        setEditable(true);
    }


    @FXML
    private void handeChangeDetails() {
        System.out.println("📤 Submitting changes...");

        try {
            worker.setUsername(usernameField.getText());
            worker.setPassword(passwordField.getText());
            worker.setFullName(fullNameField.getText());
            worker.setEmail(emailField.getText());
            worker.setPhoneNumber(numberField.getText());
            worker.setDateOfBirth(dateOfBirthField.getValue());
            worker.setGender(genderField.getText());
            worker.setHireDate(LocalDate.now()); // Optional: keep original if not changing
            worker.setStatus(WorkerStatus.valueOf(statusCombo.getValue().toString()));
            worker.setSalary(Integer.parseInt(salaryField.getText()));
            worker.setEnclosure((Enclosure) enclosureCombo.getValue());

            new WorkerRepository().updateWorker(worker); // Make sure this method exists

            System.out.println("✅ Worker updated: " + worker.getFullName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to update worker.");
        }
    }


    public void setWorker(Worker worker) {
        this.worker = worker;

        // Pre-fill fields
        usernameField.setText(worker.getUsername());
        passwordField.setText(worker.getPassword());
        fullNameField.setText(worker.getFullName());
        emailField.setText(worker.getEmail());
        numberField.setText(worker.getPhoneNumber());
        dateOfBirthField.setValue(worker.getDateOfBirth());
        genderField.setText(worker.getGender());
        salaryField.setText(String.valueOf(worker.getSalary()));

        // Set ComboBox selections
        statusCombo.setValue(worker.getStatus());
        enclosureCombo.setValue(worker.getEnclosure());

        // Initially disable all fields
        setEditable(false);
    }

    private void setEditable(boolean editable) {
        usernameField.setEditable(editable);
        passwordField.setEditable(editable);
        fullNameField.setEditable(editable);
        emailField.setEditable(editable);
        numberField.setEditable(editable);
        dateOfBirthField.setDisable(!editable);
        genderField.setEditable(editable);
        salaryField.setEditable(editable);
        enclosureCombo.setDisable(!editable);
        statusCombo.setDisable(!editable);
    }

    public void updateWorker(Worker worker) {
        String sql = "UPDATE worker SET username=?, password=?, fullName=?, email=?, phoneNumber=?, dateOfBirth=?, gender=?, hireDate=?, status=?, salary=?, specialization=?, enclosure=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, worker.getUsername());
            stmt.setString(2, worker.getPassword());
            stmt.setString(3, worker.getFullName());
            stmt.setString(4, worker.getEmail());
            stmt.setString(5, worker.getPhoneNumber());
            stmt.setDate(6, Date.valueOf(worker.getDateOfBirth()));
            stmt.setString(7, worker.getGender());
            stmt.setDate(8, Date.valueOf(worker.getHireDate()));
            stmt.setString(9, worker.getStatus().toString());
            stmt.setInt(10, worker.getSalary());
            stmt.setString(11, worker.getSpecialization().toString());
            stmt.setLong(12, worker.getEnclosure().getId());
            stmt.setLong(13, worker.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
