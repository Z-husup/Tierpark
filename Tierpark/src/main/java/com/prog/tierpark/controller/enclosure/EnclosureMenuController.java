package com.prog.tierpark.controller.enclosure;

import com.prog.tierpark.Application;
import com.prog.tierpark.SceneContext;
import com.prog.tierpark.Session;
import com.prog.tierpark.controller.animal.AnimalController;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.repository.AnimalRepository;
import com.prog.tierpark.repository.ScheduleRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the enclosure details menu.
 * Displays details about a selected enclosure, including its animals and schedules.
 * Allows users to add new schedules and navigate to animal detail views.
 */
public class EnclosureMenuController {

    /** The currently selected enclosure being displayed. */
    private Enclosure enclosure;

    // === FXML UI components ===
    @FXML private Text enclosureNameLabel;
    @FXML private Text enclosureTypeLabel;
    @FXML private Text enclosureStatusLabel;
    @FXML private Text enclosureCapacityLabel;
    @FXML private Text enclosureDescriptionLabel;
    @FXML private Text enclosureConditionLabel;

    @FXML private ListView<Schedule> scheduleViewList;
    @FXML private ListView<Animal> animalsViewList;

    /**
     * Called automatically by JavaFX after the FXML is loaded.
     * Initializes the controller by setting the selected enclosure from the global SceneContext.
     */
    @FXML
    public void initialize() {
        if (SceneContext.selectedEnclosure != null) {
            setEnclosure(SceneContext.selectedEnclosure);
        }
    }

    /**
     * Refreshes the animal and schedule list views based on the current enclosure.
     * Clears the views if the enclosure or its ID is null.
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
            List<Schedule> schedules = new ScheduleRepository().getSchedulesByEnclosureId(enclosure.getId());
            scheduleViewList.getItems().setAll(schedules);
        } else {
            scheduleViewList.getItems().clear();
        }
    }

    /**
     * Called externally to inject the selected enclosure into this controller.
     * Also sets the label values and populates animal/schedule lists.
     *
     * @param enclosure the enclosure to be displayed and managed
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;

        enclosureNameLabel.setText(enclosure.getName());
        enclosureTypeLabel.setText(enclosure.getType());
        enclosureStatusLabel.setText(enclosure.getStatus());
        enclosureCapacityLabel.setText(String.valueOf(enclosure.getCapacity()));
        enclosureDescriptionLabel.setText(enclosure.getDescription());
        enclosureConditionLabel.setText(enclosure.getCondition());

        refreshViewLists();
    }

    /**
     * Navigates the user to the appropriate main menu view based on login role (worker or admin).
     */
    @FXML
    private void toMainMenu() {
        System.out.println("Navigating to Main Menu");
        if (Session.getLoggedInWorker() != null){
            Application.switchScene("worker-menu-view.fxml");
        } else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

    /**
     * Goes back to the previous page. Redirects workers to the enclosure list and admins to the admin menu.
     */
    @FXML
    private void goBack() {
        System.out.println("Going back to previous page");
        if (Session.getLoggedInWorker() != null){
            Application.switchScene("enclosure-list-view.fxml");
        } else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

    /**
     * Opens a dialog window for creating a new schedule for this enclosure.
     * Refreshes the schedule list after the dialog is closed.
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
     * Deletes the currently selected schedule from the list and repository.
     * Updates the schedule view list on success.
     */
    @FXML
    private void handleScheduleDelete() {
        Schedule selected = scheduleViewList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean deleted = new ScheduleRepository().deleteSchedule(selected.getId());
            if (deleted) {
                scheduleViewList.getItems().remove(selected);
                System.out.println("✅ Schedule deleted.");
            } else {
                System.out.println("❌ Failed to delete schedule.");
            }
        }
    }

    /**
     * Opens the detailed view dialog for the selected animal from the list.
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
}
