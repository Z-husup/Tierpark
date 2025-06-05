package com.prog.tierpark.controller.enclosure;

import com.prog.tierpark.Application;
import com.prog.tierpark.model.AnimalFood;
import com.prog.tierpark.repository.AnimalFoodRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for managing animal food data within the zoo application.
 * Supports viewing, creating, and updating food records via a JavaFX user interface.
 */
public class AnimalFoodController {

    /** Currently selected food item from the list view. */
    private AnimalFood selectedFood;

    /** Repository for accessing and modifying animal food data. */
    private final AnimalFoodRepository animalFoodRepository = new AnimalFoodRepository();

    // === FXML-injected UI components ===
    @FXML private TextField foodNameField;
    @FXML private TextField foodAuantityField;
    @FXML private TextField foodWeightField;
    @FXML private TextField conditionField;
    @FXML private DatePicker expirationDatePicker;
    @FXML private ListView<AnimalFood> foodStorageListView;

    /**
     * Initializes the controller. Loads food data into the list view and sets up
     * a listener to populate form fields when a food item is selected.
     */
    @FXML
    public void initialize() {
        refreshFoodList();

        foodStorageListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedFood = newVal;
                foodNameField.setText(newVal.getName());
                foodAuantityField.setText(String.valueOf(newVal.getQuantity()));
                foodWeightField.setText(String.valueOf(newVal.getWeight()));
                conditionField.setText(newVal.getStorageCondition());
                expirationDatePicker.setValue(newVal.getExpirationDate());
            }
        });
    }

    /**
     * Handles the creation of a new food record using the form input.
     * Adds the food to the repository and refreshes the list view.
     */
    @FXML
    private void handeNewFood() {
        try {
            String name = foodNameField.getText();
            int quantity = Integer.parseInt(foodAuantityField.getText());
            int weight = Integer.parseInt(foodWeightField.getText());
            String condition = conditionField.getText();
            LocalDate expiration = expirationDatePicker.getValue();

            AnimalFood food = new AnimalFood(null, name, quantity, weight, LocalDate.now(), expiration, condition);

            boolean success = animalFoodRepository.addFood(food);
            if (success) {
                System.out.println("✅ Food added.");
                refreshFoodList();
                clearForm();
            } else {
                System.out.println("❌ Failed to add food.");
            }
        } catch (Exception e) {
            System.err.println("❌ Invalid food input.");
            e.printStackTrace();
        }
    }

    /**
     * Updates the list view with all food records from the repository.
     */
    private void refreshFoodList() {
        List<AnimalFood> allFood = animalFoodRepository.getAllFood();
        foodStorageListView.getItems().setAll(allFood);
    }

    /**
     * Clears all form input fields and resets the date picker.
     */
    private void clearForm() {
        foodNameField.clear();
        foodAuantityField.clear();
        foodWeightField.clear();
        conditionField.clear();
        expirationDatePicker.setValue(null);
    }

    /**
     * Navigates to the admin main menu scene.
     */
    @FXML
    private void toMainMenu() {
        Application.switchScene("admin-menu-view.fxml");
    }

    /**
     * Navigates back to the enclosure menu scene.
     */
    @FXML
    private void goBack() {
        Application.switchScene("enclosure-menu-view.fxml");
    }

    /**
     * Applies the changes made in the form to the selected food item.
     * Updates the record in the repository and refreshes the view.
     */
    @FXML
    private void handleApplyChanges() {
        if (selectedFood == null) {
            System.out.println("⚠️ No food selected to update.");
            return;
        }

        try {
            selectedFood.setName(foodNameField.getText());
            selectedFood.setQuantity(Integer.parseInt(foodAuantityField.getText()));
            selectedFood.setWeight(Integer.parseInt(foodWeightField.getText()));
            selectedFood.setStorageCondition(conditionField.getText());
            selectedFood.setExpirationDate(expirationDatePicker.getValue());
            selectedFood.setDeliveryDate(LocalDate.now()); // Optionally set to current date

            boolean success = animalFoodRepository.updateFood(selectedFood);
            if (success) {
                System.out.println("✅ Food updated.");
                refreshFoodList();
                clearForm();
                selectedFood = null;
            } else {
                System.out.println("❌ Failed to update food.");
            }

        } catch (Exception e) {
            System.err.println("❌ Invalid food update input.");
            e.printStackTrace();
        }
    }
}
