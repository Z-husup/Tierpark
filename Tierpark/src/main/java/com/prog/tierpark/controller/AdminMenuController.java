package com.prog.tierpark.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class AdminMenuController {

    @FXML
    private ListView enclosureListView;

    @FXML
    private ListView workerListView;

    @FXML
    public void initialize() {
        // Optional: add logic to initialize the list views
    }

    @FXML
    private void handleAddNewWorker() {
        System.out.println("Add new worker button clicked");
        // Add your logic here
    }

    @FXML
    private void handleAddNewEnclosure() {
        System.out.println("Add new enclosure button clicked");
        // Add your logic here
    }

    @FXML
    private void handleDeleteEnclosure() {
        System.out.println("Delete enclosure button clicked");
        // Add your logic here
    }

    @FXML
    private void handleDeleteWorker() {
        System.out.println("Delete worker button clicked");
        // Add your logic here
    }

}
