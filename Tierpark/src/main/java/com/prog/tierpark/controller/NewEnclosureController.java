package com.prog.tierpark.controller;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.repository.EnclosureRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewEnclosureController {

    private final EnclosureRepository enclosureRepository = new EnclosureRepository();

    @FXML private TextField enclosureNameField;
    @FXML private TextField enclosureZoneField;
    @FXML private TextField enclosureStatusField;
    @FXML private TextField enclosureTypeField;
    @FXML private TextField enclosureCapacityField;
    @FXML private TextField enclosureDescriptionField;
    @FXML private TextField enclosureConditionField;
    @FXML private TextField enclosureAreaField;

    @FXML
    private void handleCreateNewEnclosure() {
        try {
            Enclosure enclosure = new Enclosure(
                    0L,
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

    @FXML
    private void toMainMenu() {
        System.out.println("🔁 Navigate to Main Menu");
        // TODO: scene switching if required
    }

    @FXML
    private void goBack() {
        System.out.println("⬅️ Return to previous page");
        // TODO: scene switching if required
    }
}

