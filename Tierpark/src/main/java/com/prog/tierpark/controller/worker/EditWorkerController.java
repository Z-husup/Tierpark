package com.prog.tierpark.controller.worker;

import java.time.LocalDate;

import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.model.enums.WorkerStatus;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.service.WorkerService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for editing an existing worker's information.
 */
public class EditWorkerController {

    private Worker worker;

    private final EnclosureRepository enclosureRepository = new EnclosureRepository();

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
    @FXML private Button changeDetailsButton;

    /**
     * Initializes the UI elements and loads enclosure and status data.
     */
    @FXML
    public void initialize() {
        statusCombo.getItems().addAll(WorkerStatus.values());
        enclosureCombo.getItems().addAll(enclosureRepository.getAllEnclosures());
    }

    /**
     * Enables editing of worker fields.
     */
    @FXML
    private void handleEditingMode() {
        System.out.println("🔓 Editing mode enabled");
        setEditable(true);
    }

    /**
     * Applies edited changes to the current worker object and saves to the database.
     */
    @FXML
    private void handeChangeDetails() {
        try {
            worker.setUsername(usernameField.getText());
            worker.setPassword(passwordField.getText());
            worker.setFullName(fullNameField.getText());
            worker.setEmail(emailField.getText());
            worker.setPhoneNumber(numberField.getText());
            worker.setDateOfBirth(dateOfBirthField.getValue());
            worker.setGender(genderField.getText());
            worker.setHireDate(LocalDate.now());
            worker.setStatus((WorkerStatus) statusCombo.getValue());
            worker.setSalary(Integer.parseInt(salaryField.getText()));
            worker.setEnclosure((Enclosure) enclosureCombo.getValue());

            boolean success = new WorkerService().updateWorker(worker);
            System.out.println(success ? "✅ Worker updated." : "❌ Update failed.");

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a worker object into the form for viewing or editing.
     * @param worker the Worker to load.
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
        usernameField.setText(worker.getUsername());
        passwordField.setText(worker.getPassword());
        fullNameField.setText(worker.getFullName());
        emailField.setText(worker.getEmail());
        numberField.setText(worker.getPhoneNumber());
        dateOfBirthField.setValue(worker.getDateOfBirth());
        genderField.setText(worker.getGender());
        salaryField.setText(String.valueOf(worker.getSalary()));
        statusCombo.setValue(worker.getStatus());
        enclosureCombo.setValue(worker.getEnclosure());
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

        changeDetailsButton.setDisable(!editable);
    }
}
