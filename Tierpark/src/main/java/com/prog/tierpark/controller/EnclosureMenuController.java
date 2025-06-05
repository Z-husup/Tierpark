package com.prog.tierpark.controller;

import com.prog.tierpark.Application;
import com.prog.tierpark.Session;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.repository.ScheduleRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        animalsViewList.getItems().setAll(enclosure.getAnimals());
        scheduleViewList.getItems().setAll(enclosure.getSchedules());
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
        // TODO: open schedule creation dialog and refresh list after
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

