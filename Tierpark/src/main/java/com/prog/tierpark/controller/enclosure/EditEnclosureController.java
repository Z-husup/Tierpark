package com.prog.tierpark.controller.enclosure;

import com.prog.tierpark.Application;
import com.prog.tierpark.controller.animal.NewAnimalController;
import com.prog.tierpark.controller.animal.AnimalController;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.repository.AnimalRepository;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.ScheduleRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for editing an enclosure's information, associated animals, and schedules.
 * Allows the user to modify enclosure properties, add/remove schedules, and manage assigned animals.
 */
public class EditEnclosureController {

    /** The enclosure being edited. */
    private Enclosure enclosure;

    /** Repository for accessing and updating enclosure data. */
    private final EnclosureRepository enclosureRepository = new EnclosureRepository();

    /** Repository for managing schedules assigned to enclosures. */
    private final ScheduleRepository scheduleRepository = new ScheduleRepository();

    // === FXML UI components ===
    @FXML private TextField enclosureNameField;
    @FXML private TextField enclosureTypeField;
    @FXML private TextField enclosureStatusField;
    @FXML private TextField enclosureCapacityField;
    @FXML private TextField enclosureDescriptionField;
    @FXML private TextField enclosureConditionField;

    @FXML private ListView<Schedule> scheduleViewList;
    @FXML private ListView<Animal> animalsViewList;

    /**
     * Initializes the view after FXML loading.
     * Loads associated animals and schedules into the list views.
     */
    @FXML
    private void initialize() {
        refreshViewLists();
    }

    /**
     * Sets the enclosure to be edited and populates the form fields with its data.
     *
     * @param enclosure the enclosure to be edited
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;

        enclosureNameField.setText(enclosure.getName());
        enclosureTypeField.setText(enclosure.getType());
        enclosureStatusField.setText(enclosure.getStatus());
        enclosureCapacityField.setText(String.valueOf(enclosure.getCapacity()));
        enclosureDescriptionField.setText(enclosure.getDescription());
        enclosureConditionField.setText(enclosure.getCondition());

        refreshViewLists();
    }

    /**
     * Opens a dialog to create a new schedule assigned to this enclosure.
     * Refreshes the schedule list view after the dialog is closed.
     */
    @FXML
    private void handleAddNewSchedule() {
        System.out.println("➕ Add new schedule to: " + enclosure.getName());

        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/prog/tierpark/new-schedule-view.fxml"));
            Parent root = loader.load();

            NewScheduleController controller = loader.getController();
            controller.setEnclosure(enclosure);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create New Schedule");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            refreshViewLists();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes both the schedule and animal lists based on the current enclosure.
     * Clears the lists if no enclosure is set or ID is null.
     */
    private void refreshViewLists() {
        if (enclosure == null) return;

        if (enclosure.getId() != null) {
            List<Animal> animals = new AnimalRepository().getAnimalsByEnclosureId(enclosure.getId());
            animalsViewList.getItems().setAll(animals);
        } else {
            animalsViewList.getItems().clear();
        }

        if (enclosure.getId() != null) {
            List<Schedule> schedules = scheduleRepository.getSchedulesByEnclosureId(enclosure.getId());
            scheduleViewList.getItems().setAll(schedules);
        } else {
            scheduleViewList.getItems().clear();
        }
    }

    /**
     * Deletes the currently selected schedule from the list and repository.
     * Updates the view if deletion was successful.
     */
    @FXML
    private void handleScheduleDelete() {
        Schedule selected = scheduleViewList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean deleted = scheduleRepository.deleteSchedule(selected.getId());
            if (deleted) {
                scheduleViewList.getItems().remove(selected);
                System.out.println("✅ Schedule deleted.");
            } else {
                System.out.println("❌ Failed to delete schedule.");
            }
        }
    }

    /**
     * Opens the new animal creation dialog.
     * The created animal will later be manually assigned to an enclosure.
     */
    @FXML
    private void handleNewAnimal() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("new-animal-view.fxml"));
            Parent root = loader.load();

            NewAnimalController controller = loader.getController();
            controller.setEnclosure(enclosure);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Animal Details");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the detailed view of the selected animal in a modal dialog.
     */
    @FXML
    private void toAnimalManagePage() {
        Animal selected = animalsViewList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Application.class.getResource("animal-view.fxml"));
                Parent root = loader.load();

                AnimalController controller = loader.getController();
                controller.setAnimal(selected);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Animal Details");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setScene(new Scene(root));
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ No animal selected.");
        }
    }

    /**
     * Placeholder method to return to the main menu. Can be extended to change scenes.
     */
    @FXML
    private void toMainMenu() {
        System.out.println("🔁 Return to main menu");
        // TODO: switch scene if needed
    }

    /**
     * Closes the current window and returns to the previous view.
     */
    @FXML
    private void goBack() {
        Stage stage = (Stage) enclosureNameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Saves the updated enclosure details from the form into the database.
     * Closes the dialog window upon successful update.
     */
    @FXML
    private void handleSave() {
        try {
            enclosure.setName(enclosureNameField.getText());
            enclosure.setType(enclosureTypeField.getText());
            enclosure.setStatus(enclosureStatusField.getText());
            enclosure.setCapacity(Integer.parseInt(enclosureCapacityField.getText()));
            enclosure.setDescription(enclosureDescriptionField.getText());
            enclosure.setCondition(enclosureConditionField.getText());

            boolean updated = enclosureRepository.updateEnclosure(enclosure);
            if (updated) {
                System.out.println("✅ Enclosure updated.");
                Stage stage = (Stage) enclosureNameField.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("❌ Failed to update enclosure.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
