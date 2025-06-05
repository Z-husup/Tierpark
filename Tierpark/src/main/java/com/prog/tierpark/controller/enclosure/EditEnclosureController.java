package com.prog.tierpark.controller.enclosure;

import com.prog.tierpark.Application;
import com.prog.tierpark.controller.animal.NewAnimalController;
import com.prog.tierpark.controller.animal.AnimalController;
import com.prog.tierpark.model.Animal;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.ScheduleRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EditEnclosureController {

    private Enclosure enclosure;

    private final EnclosureRepository enclosureRepository = new EnclosureRepository();
    private final ScheduleRepository scheduleRepository = new ScheduleRepository();

    @FXML private TextField enclosureNameField;
    @FXML private TextField enclosureTypeField;
    @FXML private TextField enclosureStatusField;
    @FXML private TextField enclosureCapacityField;
    @FXML private TextField enclosureDescriptionField;
    @FXML private TextField enclosureConditionField;

    @FXML private ListView<Schedule> scheduleViewList;
    @FXML private ListView<Animal> animalsViewList;


    @FXML
    private void initialize() {
        refreshViewLists();
    }

    /**
     * Called externally to inject the Enclosure to be edited.
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;

        enclosureNameField.setText(enclosure.getName());
        enclosureTypeField.setText(enclosure.getType());
        enclosureStatusField.setText(enclosure.getStatus());
        enclosureCapacityField.setText(String.valueOf(enclosure.getCapacity()));
        enclosureDescriptionField.setText(enclosure.getDescription());
        enclosureConditionField.setText(enclosure.getCondition());
    }

    @FXML
    private void handleAddNewSchedule() {
        System.out.println("➕ Add new schedule to: " + enclosure.getName());

        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/prog/tierpark/new-schedule-view.fxml"));
            Parent root = loader.load();

            NewScheduleController controller = loader.getController();
            controller.setEnclosure(enclosure);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create New Schedule");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            refreshViewLists(); // Refresh after the dialog closes

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshViewLists() {
        if (enclosure == null) return;

        if (enclosure.getAnimals() != null) {
            animalsViewList.getItems().setAll(enclosure.getAnimals());
        } else {
            animalsViewList.getItems().clear();
        }

        if (enclosure.getId() != null) {
            List<Schedule> schedules = new ScheduleRepository().getSchedulesByEnclosureId(enclosure.getId());
            scheduleViewList.getItems().setAll(schedules);
        } else {
            scheduleViewList.getItems().clear();
        }
    }


    @FXML
    private void handleScheduleDelete() {
        Schedule selected = scheduleViewList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean deleted = scheduleRepository.deleteSchedule(selected.getId());
            if (deleted) {
                scheduleViewList.getItems().remove(selected);
                System.out.println("✅ Schedule deleted.");
            } else {
                System.out.println("❌ Failed to delete schedule.");
            }
        }
    }

    @FXML
    private void handleNewAnimal() {
        System.out.println("➕ Add new animal to: " + enclosure.getName());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/prog/tierpark/view/new-animal-form.fxml"));
            Parent root = loader.load();

            NewAnimalController controller = loader.getController();
            controller.setEnclosure(enclosure);

            Stage stage = new Stage();
            stage.setTitle("Add Animal");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // refresh animal list after dialog closes
            refreshViewLists();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void toAnimalManagePage() {
        Animal selected = animalsViewList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("🔍 Manage animal: " + selected.getName());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/prog/tierpark/view/animal-details.fxml"));
                Parent root = loader.load();

                AnimalController controller = loader.getController();
                controller.setAnimal(selected);

                Stage stage = new Stage();
                stage.setTitle("Animal Details");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void toMainMenu() {
        System.out.println("🔁 Return to main menu");
        // TODO: switch scene if needed
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) enclosureNameField.getScene().getWindow();
        stage.close();
    }


    /**
     * Saves the edited enclosure values back to the database.
     */
    @FXML
    private void handleSave() {
        try {
            enclosure.setName(enclosureNameField.getText());
            enclosure.setType(enclosureTypeField.getText());
            enclosure.setStatus(enclosureStatusField.getText());
            enclosure.setCapacity(Integer.parseInt(enclosureCapacityField.getText()));
            enclosure.setDescription(enclosureDescriptionField.getText());
            enclosure.setCondition(enclosureConditionField.getText());

            boolean updated = enclosureRepository.updateEnclosure(enclosure);
            if (updated) {
                System.out.println("✅ Enclosure updated.");
                Stage stage = (Stage) enclosureNameField.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("❌ Failed to update enclosure.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
