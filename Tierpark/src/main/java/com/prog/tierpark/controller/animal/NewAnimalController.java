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

public class NewAnimalController {

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

    @FXML
    public void initialize() {
        animalGroupCombo.getItems().addAll(AnimalGroup.values());
        genderCombo.getItems().addAll(AnimalGender.values());
        healthStatusCombo.getItems().addAll(HealthStatus.values());

        EnclosureRepository repo = new EnclosureRepository();
        enclosureCombo.getItems().addAll(repo.getAllEnclosures());
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
        if (enclosure != null) {
            enclosureCombo.setValue(enclosure);
            enclosureCombo.setDisable(true); // Lock if preset
        }
    }

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

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
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
        Stage stage = (Stage) animalNameField.getScene().getWindow();
        stage.close();
    }

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
