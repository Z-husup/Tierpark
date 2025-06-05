package com.prog.tierpark.controller.enclosure;

import java.util.List;

import com.prog.tierpark.Application;
import com.prog.tierpark.Session;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.repository.EnclosureRepository;

import com.prog.tierpark.service.AdminService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * Controller for displaying a list of zoo enclosures and their basic information.
 * Allows navigation to edit or view individual enclosures and return to the main menu.
 */
public class EnclosureListController {

    /** Service class used to handle administrative actions such as opening the edit dialog. */
    private final AdminService adminService = new AdminService();

    // === FXML UI components ===
    @FXML private ListView<Enclosure> enclosureListView;

    @FXML private Text enclosureNameLabel;
    @FXML private Text enclosureTypeLabel;
    @FXML private Text enclosureStatusLabel;
    @FXML private Text enclosureCapacityLabel;
    @FXML private Text enclosureDescriptionLabel;
    @FXML private Text enclosureConditionLabel;

    /**
     * Initializes the controller. Loads all enclosures into the list view and sets up a selection listener.
     * Automatically called by JavaFX after FXML components are injected.
     */
    @FXML
    public void initialize() {
        EnclosureRepository repo = new EnclosureRepository(); // Or inject if needed
        List<Enclosure> enclosures = repo.getAllEnclosures();

        enclosureListView.getItems().addAll(enclosures);

        // Listener for displaying selected enclosure details
        enclosureListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showEnclosureDetails(newVal);
            }
        });
    }

    /**
     * Navigates to the main menu screen, depending on whether an admin or a worker is logged in.
     */
    @FXML
    private void toMainMenu() {
        System.out.println("Navigating to Main Menu");
        if (Session.getLoggedInWorker() != null) {
            Application.switchScene("worker-menu-view.fxml");
        } else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

    /**
     * Returns to the previous page. Currently redirects to the main menu.
     * Could be customized later to navigate to a specific previous view.
     */
    @FXML
    private void goBack() {
        System.out.println("Going back to previous page");
        System.out.println("Navigating to Main Menu");
        if (Session.getLoggedInWorker() != null) {
            Application.switchScene("worker-menu-view.fxml");
        } else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

    /**
     * Opens the enclosure management page and allows editing the currently selected enclosure.
     * Refreshes the list view after editing to reflect changes.
     */
    @FXML
    private void toEnclosureManagePage() {
        System.out.println("Navigating to Enclosure Management Page");
        Application.switchScene("enclosure-management-view.fxml");

        Enclosure selected = enclosureListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            adminService.openEditEnclosureDialog(selected);
            enclosureListView.refresh();
        }
    }

    /**
     * Displays detailed information about the selected enclosure in the UI text labels.
     *
     * @param enclosure the selected enclosure whose details should be shown
     */
    private void showEnclosureDetails(Enclosure enclosure) {
        enclosureNameLabel.setText(enclosure.getName());
        enclosureTypeLabel.setText(enclosure.getType());
        enclosureStatusLabel.setText(enclosure.getStatus());
        enclosureCapacityLabel.setText(String.valueOf(enclosure.getCapacity()));
        enclosureDescriptionLabel.setText(enclosure.getDescription());
        enclosureConditionLabel.setText(enclosure.getCondition());
    }
}
