package com.prog.tierpark.controller.enclosure;

import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.model.enums.ScheduleType;
import com.prog.tierpark.repository.ScheduleRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Controller for creating a new schedule for a specific enclosure.
 * Handles user input for schedule type, date, time, and description.
 * Validates input and saves the schedule via the repository.
 */
public class NewScheduleController {

    // === FXML UI components ===
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<ScheduleType> scheduleTypeCombo;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private Button addButton;

    /** The enclosure for which the schedule is being created. */
    private Enclosure enclosure;

    /**
     * Initializes the controller after the FXML has been loaded.
     * Sets up combo box values and spinners for selecting time.
     */
    @FXML
    public void initialize() {
        scheduleTypeCombo.getItems().addAll(ScheduleType.values());

        hourSpinner = new Spinner<>(0, 23, 12);
        minuteSpinner = new Spinner<>(0, 59, 0);
        hourSpinner.setEditable(true);
        minuteSpinner.setEditable(true);
    }

    /**
     * Sets the enclosure that the new schedule will be associated with.
     *
     * @param enclosure the enclosure to link this schedule to
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    /**
     * Handles the add button click. Validates input and attempts to create the schedule.
     * If successful, closes the dialog window.
     */
    @FXML
    private void handleAdd() {
        if (enclosure == null) {
            showAlert("Enclosure is not set.");
            return;
        }

        ScheduleType type = scheduleTypeCombo.getValue();
        String name = descriptionArea.getText();
        LocalDate date = datePicker.getValue();
        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();

        if (type == null || name.isBlank() || date == null) {
            showAlert("Please fill all fields.");
            return;
        }

        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
        Schedule schedule = new Schedule(null, name, type, dateTime);
        boolean success = new ScheduleRepository().addSchedule(schedule, enclosure.getId());

        if (success) {
            System.out.println("✅ Schedule created for enclosure: " + enclosure.getName());
            closeDialog();
        } else {
            showAlert("❌ Failed to create schedule.");
        }
    }

    /**
     * Displays a warning alert with the given message.
     *
     * @param msg the message to show in the alert
     */
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Closes the current dialog window.
     */
    private void closeDialog() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
