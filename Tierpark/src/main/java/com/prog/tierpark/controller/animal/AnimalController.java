package com.prog.tierpark.controller.animal;

import com.prog.tierpark.Application;
import com.prog.tierpark.Session;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.MedicalHistory;
import com.prog.tierpark.model.enums.AnimalGender;
import com.prog.tierpark.model.enums.AnimalGroup;
import com.prog.tierpark.model.enums.HealthStatus;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.MedicalHistoryRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * Controller class for managing the details of an animal in the UI.
 * Allows viewing and editing of animal properties and associated medical history.
 */
public class AnimalController {

    private Animal animal;

    @FXML private TextField animalNameField;
    @FXML private ComboBox<AnimalGroup> animalGroupCombo;
    @FXML private DatePicker dateOfBirthField;
    @FXML private DatePicker arrivalDateField;
    @FXML private ComboBox<AnimalGender> genderCombo;
    @FXML private TextField sizeField;
    @FXML private TextField weightField;
    @FXML private ComboBox<HealthStatus> healthStatusCombo;
    @FXML private ComboBox<Enclosure> enclosureCombo;

    @FXML private ListView<MedicalHistory> medicalRecordsListView;
    @FXML private ImageView animalGroupImage;
    @FXML private Button editAnimalButton;

    private boolean editingMode = false;

    /**
     * Sets the current animal and populates the fields in the view.
     *
     * @param animal the animal to display and potentially edit
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
        populateFields();
        updateImage();
    }

    /**
     * Initializes the combo boxes with values and sets up the UI components.
     * Called automatically by the JavaFX framework after the FXML elements are loaded.
     */
    @FXML
    public void initialize() {
        animalGroupCombo.getItems().addAll(AnimalGroup.values());
        genderCombo.getItems().addAll(AnimalGender.values());
        healthStatusCombo.getItems().addAll(HealthStatus.values());
        enclosureCombo.getItems().addAll(new EnclosureRepository().getAllEnclosures());
    }

    /**
     * Fills all UI fields with the current animal's data, loads medical history and image.
     */
    private void populateFields() {
        if (animal == null) return;

        animalNameField.setText(animal.getName());
        animalGroupCombo.setValue(animal.getAnimalGroup());
        dateOfBirthField.setValue(animal.getDateOfBirth());
        arrivalDateField.setValue(animal.getArrivalDate());
        genderCombo.setValue(animal.getGender());
        sizeField.setText(String.valueOf(animal.getSize()));
        weightField.setText(String.valueOf(animal.getWeight()));
        healthStatusCombo.setValue(animal.getHealthStatus());
        enclosureCombo.setValue(animal.getEnclosure());

        if (animal.getAnimalGroup() != null) {
            try {
                String imgPath = "com/prog/tierpark/images/" + animal.getAnimalGroup().name().toLowerCase() + ".jpg";
                InputStream imageStream = getClass().getResourceAsStream(imgPath);

                if (imageStream != null) {
                    animalGroupImage.setImage(new Image(imageStream));
                } else {
                    System.err.println("❌ Image not found at: " + imgPath);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Image not found for group: " + animal.getAnimalGroup());
            }
        }

        if (animal.getId() != null) {
            medicalRecordsListView.getItems().setAll(
                    new MedicalHistoryRepository().getMedicalRecordsByAnimalId(animal.getId())
            );
        }
    }

    /**
     * Handles toggling of edit mode. Enables or disables form fields accordingly.
     * Saves the changes if edit mode is turned off.
     */
    @FXML
    private void handleEditAnimal() {
        editingMode = !editingMode;

        animalNameField.setDisable(!editingMode);
        animalGroupCombo.setDisable(!editingMode);
        dateOfBirthField.setDisable(!editingMode);
        arrivalDateField.setDisable(!editingMode);
        genderCombo.setDisable(!editingMode);
        sizeField.setDisable(!editingMode);
        weightField.setDisable(!editingMode);
        healthStatusCombo.setDisable(!editingMode);
        enclosureCombo.setDisable(!editingMode);

        editAnimalButton.setDisable(false);

        if (!editingMode) {
            saveAnimalChanges();
        }
    }

    /**
     * Saves the updated values from the UI into the current animal object.
     * Shows an alert in case of validation errors.
     */
    private void saveAnimalChanges() {
        try {
            animal.setName(animalNameField.getText());
            animal.setAnimalGroup(animalGroupCombo.getValue());
            animal.setDateOfBirth(dateOfBirthField.getValue());
            animal.setArrivalDate(arrivalDateField.getValue());
            animal.setGender(genderCombo.getValue());
            animal.setSize(Integer.parseInt(sizeField.getText()));
            animal.setWeight(Integer.parseInt(weightField.getText()));
            animal.setHealthStatus(healthStatusCombo.getValue());
            animal.setEnclosure(enclosureCombo.getValue());

            System.out.println("✅ Animal updated: " + animal.getName());

            // Optional: persist the animal changes to the database here

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Invalid Input", "Make sure all fields are correctly filled.");
        }
    }

    /**
     * Handles the action to add a medical record for the current animal.
     * Currently only logs the action; can be expanded to open a dialog.
     */
    @FXML
    private void handleAddMedicalRecord() {
        if (animal != null && animal.getId() != null) {
            System.out.println("➕ Add medical record for: " + animal.getName());
            // Optional: open a dialog to add a new record
        }
    }

    /**
     * Navigates the user to the main menu, depending on the logged-in worker or admin.
     */
    @FXML
    private void toMainMenu() {
        System.out.println("Navigating to Main Menu");
        if (Session.getLoggedInWorker() != null){
            Application.switchScene("worker-menu-view.fxml");
        } else {
            Application.switchScene("admin-menu-view.fxml");
        }
    }

    /**
     * Closes the current window and returns to the previous screen.
     */
    @FXML
    private void goBack() {
        Stage stage = (Stage) animalNameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an alert box with the given title and message content.
     *
     * @param title   the title of the alert dialog
     * @param content the message body of the alert
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Updates the image view based on the selected animal group from the combo box.
     */
    @FXML
    private void updateImage() {
        animalGroupImage.setImage(null);

        String imgPath = "/com/prog/tierpark/images/" + animalGroupCombo.getValue() + ".jpg";
        InputStream imageStream = getClass().getResourceAsStream(imgPath);

        if (imageStream != null) {
            animalGroupImage.setImage(new Image(imageStream));
        } else {
            System.err.println("❌ Image not found at: " + imgPath);
        }
    }
}
