package com.prog.tierpark.controller.animal;

import com.prog.tierpark.Application;
import com.prog.tierpark.Session;
import com.prog.tierpark.model.*;
import com.prog.tierpark.model.enums.AnimalGender;
import com.prog.tierpark.model.enums.AnimalGroup;
import com.prog.tierpark.model.enums.HealthStatus;
import com.prog.tierpark.repository.AnimalRepository;
import com.prog.tierpark.repository.EnclosureRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * Controller class for creating a new animal entry in the system.
 * Provides form fields to enter all relevant animal data and submit it to the database.
 */
public class NewAnimalController {

    /** The pre-selected enclosure, if set from the parent view. */
    private Enclosure enclosure;

    @FXML private TextField animalNameField;
    @FXML private DatePicker dateOfBirthField;
    @FXML private DatePicker arrivalDateField;
    @FXML private ComboBox<AnimalGroup> animalGroupCombo;
    @FXML private ComboBox<AnimalGender> genderCombo;
    @FXML private ComboBox<HealthStatus> healthStatusCombo;
    @FXML private ComboBox<Enclosure> enclosureCombo;
    @FXML private TextField sizeField;
    @FXML private TextField weightField;
    @FXML private ImageView animalGroupImage;

    /**
     * Initializes the combo boxes and populates them with enums and available enclosures.
     * Called automatically by JavaFX after the FXML components are loaded.
     */
    @FXML
    public void initialize() {
        animalGroupCombo.getItems().addAll(AnimalGroup.values());
        genderCombo.getItems().addAll(AnimalGender.values());
        healthStatusCombo.getItems().addAll(HealthStatus.values());

        EnclosureRepository repo = new EnclosureRepository();
        enclosureCombo.getItems().addAll(repo.getAllEnclosures());
    }

    /**
     * Sets the enclosure if it is preselected by the caller and disables further editing of that field.
     *
     * @param enclosure the preselected enclosure to lock into the form
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
        if (enclosure != null) {
            enclosureCombo.setValue(enclosure);
            enclosureCombo.setDisable(true);
        }
    }

    /**
     * Handles the creation of a new animal based on the input fields.
     * Validates input, constructs the Animal object, and adds it to the database.
     */
    @FXML
    private void handleCreateNewAnimal() {
        try {
            Animal newAnimal = new Animal(
                    null,
                    animalNameField.getText(),
                    animalGroupCombo.getValue(),
                    dateOfBirthField.getValue(),
                    arrivalDateField.getValue(),
                    calculateAge(dateOfBirthField.getValue()),
                    genderCombo.getValue(),
                    Integer.parseInt(sizeField.getText()),
                    Integer.parseInt(weightField.getText()),
                    healthStatusCombo.getValue(),
                    null,
                    enclosureCombo.getValue()
            );

            boolean success = new AnimalRepository().addAnimal(newAnimal);
            if (success) {
                System.out.println("✅ Animal added: " + newAnimal.getName());
                Stage stage = (Stage) animalNameField.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Error", "Failed to add animal.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Input Error", "Please ensure all fields are valid.");
        }
    }

    /**
     * Calculates the age of the animal based on its birth year.
     *
     * @param birthDate the birth date of the animal
     * @return the age in years, or 0 if birthDate is null
     */
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    /**
     * Displays an alert dialog with the given title and message.
     *
     * @param title the title of the alert
     * @param msg   the message to be displayed
     */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Navigates the user back to the main menu view.
     * Decides between worker and admin views based on the logged-in user.
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
     * Updates the displayed image based on the selected animal group.
     * If the image is not found, prints an error to the console.
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
