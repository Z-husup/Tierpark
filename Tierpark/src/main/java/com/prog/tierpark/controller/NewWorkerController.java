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

public class NewWorkerController {

    private WorkerRepository workerRepository = new WorkerRepository();

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
    private void handleAddNewWorker() {
        System.out.println("Adding new worker...");

        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String phone = numberField.getText();
            LocalDate dob = dateOfBirthField.getValue();
            String gender = genderField.getText();

            WorkerStatus status = (WorkerStatus) statusCombo.getValue();
            WorkerSpecialization specialization = WorkerSpecialization.SUPERVISION;
            Enclosure enclosure = (Enclosure) enclosureCombo.getValue();

            int salary = Integer.parseInt(salaryField.getText());

            LocalDate hireDate = LocalDate.now(); // or get from a date picker if available

            Worker worker = new Worker(
                    null,
                    username,
                    password,
                    fullName,
                    email,
                    phone,
                    dob,
                    gender,
                    hireDate,
                    status,
                    salary,
                    specialization,
                    enclosure
            );

            workerRepository.addWorker(worker);
            System.out.println("✅ Worker created: " + fullName);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to create worker.");
        }
    }

}
