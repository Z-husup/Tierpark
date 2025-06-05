package com.prog.tierpark.controller.enclosure;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.repository.EnclosureRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for creating a new enclosure within the zoo management system.
 * Collects user input through a form and submits it to the repository.
 */
public class NewEnclosureController {

    /** Repository for handling database operations related to enclosures. */
    private final EnclosureRepository enclosureRepository = new EnclosureRepository();

    // === FXML UI input fields ===
    @FXML private TextField enclosureNameField;
    @FXML private TextField enclosureZoneField;
    @FXML private TextField enclosureStatusField;
    @FXML private TextField enclosureTypeField;
    @FXML private TextField enclosureCapacityField;
    @FXML private TextField enclosureDescriptionField;
    @FXML private TextField enclosureConditionField;
    @FXML private TextField enclosureAreaField;

    /**
     * Handles the creation of a new enclosure based on the input field values.
     * Adds the new enclosure to the repository and closes the current window if successful.
     */
    @FXML
    private void handleCreateNewEnclosure() {
        try {
            Enclosure enclosure = new Enclosure(
                    0L, // Temporary ID, assuming auto-increment in DB
                    enclosureNameField.getText(),
                    enclosureZoneField.getText(),
                    enclosureStatusField.getText(),
                    enclosureTypeField.getText(),
                    Integer.parseInt(enclosureCapacityField.getText()),
                    enclosureDescriptionField.getText(),
                    enclosureConditionField.getText(),
                    enclosureAreaField.getText()
            );

            boolean success = enclosureRepository.addEnclosure(enclosure);
            if (success) {
                System.out.println("✅ Enclosure created.");
                Stage stage = (Stage) enclosureNameField.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("❌ Failed to create enclosure.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Placeholder method for navigating to the main menu.
     * Extend this method to switch scenes if needed.
     */
    @FXML
    private void toMainMenu() {
        System.out.println("🔁 Navigate to Main Menu");
        // TODO: scene switching if required
    }

    /**
     * Placeholder method for returning to the previous page.
     * Extend this method to switch scenes if needed.
     */
    @FXML
    private void goBack() {
        System.out.println("⬅️ Return to previous page");
        // TODO: scene switching if required
    }
}

