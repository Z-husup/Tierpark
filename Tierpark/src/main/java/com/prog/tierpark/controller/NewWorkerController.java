package com.prog.tierpark.controller;

import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.model.enums.WorkerSpecialization;
import com.prog.tierpark.model.enums.WorkerStatus;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.WorkerRepository;
import com.prog.tierpark.service.WorkerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for creating and adding a new worker to the system.
 */
public class NewWorkerController {

    private WorkerRepository workerRepository = new WorkerRepository();

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField numberField;
    @FXML private DatePicker dateOfBirthField;
    @FXML private TextField genderField;
    @FXML private ComboBox enclosureCombo;
    @FXML private ComboBox statusCombo;
    @FXML private TextField salaryField;

    /**
     * Initializes status and enclosure combo boxes.
     */
    @FXML
    public void initialize() {
        statusCombo.getItems().addAll(WorkerStatus.values());
        enclosureCombo.getItems().addAll(new EnclosureRepository().getAllEnclosures());
    }

    /**
     * Collects all data from input fields and creates a new Worker.
     */
    @FXML
    private void handleAddNewWorker() {
        try {
            Worker worker = new Worker(
                    null,
                    usernameField.getText(),
                    passwordField.getText(),
                    fullNameField.getText(),
                    emailField.getText(),
                    numberField.getText(),
                    dateOfBirthField.getValue(),
                    genderField.getText(),
                    LocalDate.now(),
                    (WorkerStatus) statusCombo.getValue(),
                    Integer.parseInt(salaryField.getText()),
                    WorkerSpecialization.SUPERVISION,
                    (Enclosure) enclosureCombo.getValue()
            );

            boolean success = new WorkerService().register(worker);
            System.out.println(success ? "✅ Worker created." : "❌ Failed to create.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

