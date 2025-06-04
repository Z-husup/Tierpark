package com.prog.tierpark.controller;

import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.service.AdminService;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * JavaFX controller class for the Admin Menu UI.
 * Provides functionalities to manage workers and enclosures.
 */
public class AdminMenuController {

    @FXML private ListView<Enclosure> enclosureListView;
    @FXML private ListView<Worker> workerListView;
    @FXML private Text enclosureNameLabel;
    @FXML private Text workerNameLabel;

    private final AdminService adminService = new AdminService();

    /**
     * Initializes the controller. Loads initial data and sets up selection listeners.
     */
    @FXML
    public void initialize() {
        refreshLists();

        enclosureListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) enclosureNameLabel.setText(newVal.getName());
        });

        workerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) workerNameLabel.setText(newVal.getFullName());
        });
    }

    /**
     * Refreshes the lists of workers and enclosures in the UI.
     */
    private void refreshLists() {
        enclosureListView.getItems().setAll(adminService.getAllEnclosures());
        workerListView.getItems().setAll(adminService.getAllWorkers());
    }

    /**
     * Called when the "Add Worker" button is clicked.
     * Opens the new worker dialog and refreshes the list.
     */
    @FXML
    private void handleAddNewWorker() {
        adminService.openNewWorkerDialog();
        refreshLists();
    }

    /**
     * Called when the "Edit Worker" button is clicked.
     * Opens the edit dialog for the selected worker.
     */
    @FXML
    private void handleEditWorker() {
        Worker selected = workerListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            adminService.openEditWorkerDialog(selected);
            refreshLists();
        }
    }

    /**
     * Called when the "Delete Worker" button is clicked.
     * Deletes the selected worker and refreshes the list.
     */
    @FXML
    private void handleDeleteWorker() {
        Worker selected = workerListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            adminService.deleteWorkerById(selected.getId());
            refreshLists();
        }
    }

    /**
     * Called when the "Add Enclosure" button is clicked.
     * Opens the new enclosure dialog and refreshes the list.
     */
    @FXML
    private void handleAddNewEnclosure() {
        adminService.openNewEnclosureDialog();
        refreshLists();
    }

    /**
     * Called when the "Delete Enclosure" button is clicked.
     * Deletes the selected enclosure and refreshes the list.
     */
    @FXML
    private void handleDeleteEnclosure() {
        Enclosure selected = enclosureListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            adminService.deleteEnclosureById(selected.getId());
            refreshLists();
        }
    }

    /**
     * Called when the "Edit Enclosure" button is clicked.
     * Opens the edit dialog for the selected enclosure.
     */
    @FXML
    private void toManageEnclosure() {
        Enclosure selected = enclosureListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            adminService.openEditEnclosureDialog(selected);
            refreshLists();
        }
    }
}

