package com.prog.tierpark.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class WorkerMenuController {
    @FXML
    private ListView scheduleViewList;

    @FXML
    private Text workerUsernameLabel;

    @FXML
    private Text enclosureLabel1;

    @FXML
    private Text workerFullNameLabel;

    @FXML
    private Text workerStatusLabel;

    @FXML
    private Text enclosureLabel2;

    @FXML
    private Text workerEmailLabel;

    @FXML
    private Text workerSalaryLabel;

    @FXML
    private Button toEnclosureListPage;

    @FXML
    private Button toFoodManagementPage;

    @FXML
    private Button toEnclosureManagementPage;

    @FXML
    public void initialize() {
        // TODO: Optional: populate schedule list or set worker info
    }

    @FXML
    private void toEnclosureListPage() {
        System.out.println("Navigating to Enclosure List Page");
        // TODO: Implement scene switch logic
    }

    @FXML
    private void toFoodManagementPage() {
        System.out.println("Navigating to Food Management Page");
        // TODO: Implement scene switch logic
    }

    @FXML
    private void toEnclosureManagementPage() {
        System.out.println("Navigating to Enclosure Management Page");
        // TODO: Implement scene switch logic
    }
}
