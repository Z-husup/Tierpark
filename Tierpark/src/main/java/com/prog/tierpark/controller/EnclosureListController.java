package com.prog.tierpark.controller;

import java.util.List;

import com.prog.tierpark.Application;
import com.prog.tierpark.Session;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.repository.EnclosureRepository;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class EnclosureListController {

    @FXML
    private ListView<Enclosure> enclosureListView;

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
    public void initialize() {
        EnclosureRepository repo = new EnclosureRepository(); // Or inject if needed
        List<Enclosure> enclosures = repo.getAllEnclosures();

        enclosureListView.getItems().addAll(enclosures);

        // Set selection listener
        enclosureListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showEnclosureDetails(newVal);
            }
        });
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
            Application.switchScene("worker-menu-view.fxml");
        }
        else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

    @FXML
    private void toEnclosureManagePage() {
        System.out.println("Navigating to Enclosure Management Page");
        Application.switchScene("enclosure-management-view.fxml");

        //TODO: Finish enclosure management page

    }

    private void showEnclosureDetails(Enclosure enclosure) {
        enclosureNameLabel.setText(enclosure.getName());
        enclosureTypeLabel.setText(enclosure.getType());
        enclosureStatusLabel.setText(enclosure.getStatus());
        enclosureCapacityLabel.setText(String.valueOf(enclosure.getCapacity()));
        enclosureDescriptionLabel.setText(enclosure.getDescription());
        enclosureConditionLabel.setText(enclosure.getCondition());
    }

}
