package com.prog.tierpark.service;

import com.prog.tierpark.Application;
import com.prog.tierpark.controller.enclosure.EditEnclosureController;
import com.prog.tierpark.controller.worker.EditWorkerController;
import com.prog.tierpark.model.Admin;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.repository.AdminRepository;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.WorkerRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Service class responsible for managing administrative operations,
 * including managing workers and enclosures, as well as opening dialogs.
 */
public class AdminService {

    private final WorkerRepository workerRepository = new WorkerRepository();
    private final EnclosureRepository enclosureRepository = new EnclosureRepository();
    private final AdminRepository adminRepository = new AdminRepository();

    /**
     * Retrieves all workers from the repository.
     * @return List of all workers.
     */
    public List<Worker> getAllWorkers() {
        return workerRepository.getAllWorkers();
    }

    /**
     * Retrieves all enclosures from the repository.
     * @return List of all enclosures.
     */
    public List<Enclosure> getAllEnclosures() {
        return enclosureRepository.getAllEnclosures();
    }

    /**
     * Deletes a worker by their ID.
     * @param id The ID of the worker to delete.
     */
    public void deleteWorkerById(Long id) {
        workerRepository.deleteWorker(id);
    }

    /**
     * Deletes an enclosure by its ID.
     * @param id The ID of the enclosure to delete.
     */
    public void deleteEnclosureById(Long id) {
        enclosureRepository.deleteEnclosure(id);
    }

    /**
     * Opens a dialog window to create a new worker.
     */
    public void openNewWorkerDialog() {
        openDialog("/com/prog/tierpark/new-worker-view.fxml", null);
    }

    /**
     * Opens a dialog window to edit an existing worker.
     * @param worker The worker to edit.
     */
    public void openEditWorkerDialog(Worker worker) {
        openDialog("/com/prog/tierpark/edit-worker-view.fxml", worker);
    }

    /**
     * Opens a dialog window to create a new enclosure.
     */
    public void openNewEnclosureDialog() {
        openDialog("/com/prog/tierpark/new-enclosure-view.fxml", null);
    }

    /**
     * Opens a dialog window to edit an existing enclosure.
     * @param enclosure The enclosure to edit.
     */
    public void openEditEnclosureDialog(Enclosure enclosure) {
        openDialog("/com/prog/tierpark/edit-enclosure-view.fxml", enclosure);
    }

    /**
     * Logs in an admin by checking credentials.
     *
     * @param username The username.
     * @param password The password (plain text).
     * @return Admin object if login successful, null otherwise.
     */
    public Admin login(String username, String password) {
        Admin admin = adminRepository.getAdminByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }

        return null;
    }

    /**
     * Generic method to open a dialog for editing or creating entities.
     *
     * @param fxmlPath The path to the FXML file.
     * @param data The data object to pass to the controller (optional).
     * @param <T> Type of the data object.
     */
    private <T> void openDialog(String fxmlPath, T data) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent root = loader.load();

            if (data != null) {
                if (data instanceof Enclosure enclosure) {
                    EditEnclosureController controller = loader.getController();
                    controller.setEnclosure(enclosure);
                }
                if (data instanceof Worker worker) {
                    EditWorkerController controller = loader.getController();
                    controller.setWorker(worker);
                }
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Dialog");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


