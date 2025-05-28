package com.prog.tierpark.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class EnclosureListController {

    @FXML
    private ListView enclosureListView;

    @FXML
    private Text enclosureNameLabel;

    @FXML
    private Text enclosureTypeLabel;

    @FXML
    private Text enclosureStatusLabel;

    @FXML
    private Text enclosureCapacityLabel;

    @FXML
    private Text enclosureDescriptionLabel;

    @FXML
    private Text enclosureConditionLabel;

    @FXML
    private Button toMainMenu;

    @FXML
    private Button goBack;

    @FXML
    private Button toEnclosureManagePage;

    @FXML
    public void initialize() {
        // TODO: Optional: populate enclosureListView or initialize labels
    }

    @FXML
    private void toMainMenu() {
        System.out.println("Navigating to Main Menu");
        // TODO: Navigation logic here
    }

    @FXML
    private void goBack() {
        System.out.println("Going back to previous page");
        // TODO: Go-back logic here
    }

    @FXML
    private void toEnclosureManagePage() {
        System.out.println("Navigating to Enclosure Manage Page");
        // TODO: Logic to switch scene or open management details
    }
}
