package com.prog.tierpark.controller;

import com.prog.tierpark.Application;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.WorkerRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminMenuController {

    @FXML private ListView<Enclosure> enclosureListView;
    @FXML private ListView<Worker> workerListView;

    @FXML private Text enclosureNameLabel;
    @FXML private Text workerNameLabel;



    @FXML
    public void initialize() {
        // Clear existing items to prevent duplicates
        enclosureListView.getItems().clear();
        workerListView.getItems().clear();

        // Refill enclosures
        EnclosureRepository enclosureRepo = new EnclosureRepository();
        List<Enclosure> enclosures = enclosureRepo.getAllEnclosures();
        enclosureListView.getItems().addAll(enclosures);

        // Refill workers
        WorkerRepository workerRepo = new WorkerRepository();
        List<Worker> workers = workerRepo.getAllWorkers();
        workerListView.getItems().addAll(workers);

        // Optional: reattach listeners if needed
        enclosureListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                enclosureNameLabel.setText(newVal.getName());
            }
        });

        workerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                workerNameLabel.setText(newVal.getFullName());
            }
        });
    }



    @FXML
    private void handleAddNewWorker() {
        System.out.println("Add new worker button clicked");
        openDialog("new-worker-view.fxml", null);
    }


    @FXML
    private void handleAddNewEnclosure() {
        System.out.println("Add new enclosure button clicked");

        //TODO Enclosure Creation Page

    }

    @FXML
    private void toManageEnclosure() {
        System.out.println("Manage enclosure button clicked");

        //TODO Enclosure Creation Page

    }

    @FXML
    private void handleEditWorker() {
        Worker selected = workerListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            openDialog("edit-worker-view.fxml", selected);
        } else {
            System.out.println("⚠️ No worker selected.");
        }
    }

    @FXML
    private void handleDeleteWorker() {
        Worker selected = workerListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            new WorkerRepository().deleteWorker(selected.getId());
            workerListView.getItems().remove(selected);
            System.out.println("🗑️ Worker deleted: " + selected.getFullName());
        } else {
            System.out.println("⚠️ No worker selected.");
        }
    }

    @FXML
    private void handleDeleteEnclosure() {
        Enclosure selected = enclosureListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            new EnclosureRepository().deleteEnclosure(selected.getId());
            enclosureListView.getItems().remove(selected);
            System.out.println("🗑️ Enclosure deleted: " + selected.getName());
        } else {
            System.out.println("⚠️ No enclosure selected.");
        }
    }

    private <T> void openDialog(String fxmlPath, T data) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent root = loader.load();

            if (data != null) {
                if (data instanceof Worker worker) {
                    EditWorkerController controller = loader.getController();
                    controller.setWorker(worker);
                } else if (data instanceof Enclosure enclosure) {

                    //TODO Enclosure Edit Page

                }
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Dialog");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // Refresh lists after closing dialog
            initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
