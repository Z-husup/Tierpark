package com.prog.tierpark.controller.enclosure;

import com.prog.tierpark.Application;
import com.prog.tierpark.SceneContext;
import com.prog.tierpark.Session;
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

public class EnclosureMenuController {

    private Enclosure enclosure;

    @FXML private Text enclosureNameLabel;
    @FXML private Text enclosureTypeLabel;
    @FXML private Text enclosureStatusLabel;
    @FXML private Text enclosureCapacityLabel;
    @FXML private Text enclosureDescriptionLabel;
    @FXML private Text enclosureConditionLabel;

    @FXML private ListView<Schedule> scheduleViewList;
    @FXML private ListView<Animal> animalsViewList;

    @FXML
    public void initialize() {
        if (SceneContext.selectedEnclosure != null) {
            setEnclosure(SceneContext.selectedEnclosure);
        }
    }

    private void refreshViewLists() {
        if (enclosure == null) return;

        if (enclosure.getAnimals() != null) {
            animalsViewList.getItems().setAll(enclosure.getAnimals());
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
     * Метод, вызываемый извне, чтобы передать в контроллер выбранный вольер.
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;

        // Установим значения текстовых полей
        enclosureNameLabel.setText(enclosure.getName());
        enclosureTypeLabel.setText(enclosure.getType());
        enclosureStatusLabel.setText(enclosure.getStatus());
        enclosureCapacityLabel.setText(String.valueOf(enclosure.getCapacity()));
        enclosureDescriptionLabel.setText(enclosure.getDescription());
        enclosureConditionLabel.setText(enclosure.getCondition());

        // Заполним списки
        refreshViewLists();
    }

    @FXML
    private void toMainMenu() {
        System.out.println("Navigating to Main Menu");
        if (Session.getLoggedInWorker() != null){
            Application.switchScene("worker-menu-view.fxml");
        }
        else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

    @FXML
    private void goBack() {
        System.out.println("Going back to previous page");
        System.out.println("Navigating to Main Menu");
        if (Session.getLoggedInWorker() != null){
            Application.switchScene("enclosure-list-view.fxml");
        }
        else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

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

            refreshViewLists(); // Refresh after the dialog closes

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

    @FXML
    private void toAnimalManagePage() {
        Animal selected = animalsViewList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("📋 Managing animal: " + selected.getName());
            // TODO: Open animal management UI
        }
    }
}

