package com.prog.tierpark.controller.worker;

import java.util.List;

import com.prog.tierpark.Application;
import com.prog.tierpark.SceneContext;
import com.prog.tierpark.Session;
import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.repository.ScheduleRepository;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * Controller for the Worker menu view, displaying worker info and navigation.
 */
public class WorkerMenuController {

    private static Worker worker = Session.getLoggedInWorker();

    @FXML private ListView scheduleViewList;
    @FXML private Text workerUsernameLabel;
    @FXML private Text enclosureLabel1;
    @FXML private Text workerFullNameLabel;
    @FXML private Text workerStatusLabel;
    @FXML private Text enclosureLabel2;
    @FXML private Text workerEmailLabel;
    @FXML private Text workerSalaryLabel;

    /**
     * Initializes the view with the logged-in worker’s information.
     */
    @FXML
    public void initialize() {
        if (Session.getLoggedInWorker()!= null){
            setWorker(Session.getLoggedInWorker());
        }
    }

    /**
     * Navigates to the Enclosure List page.
     */
    @FXML
    private void toEnclosureListPage() {
        System.out.println("Navigating to Enclosure List Page");
        Application.switchScene("enclosure-list-view.fxml");
    }

    /**
     * Navigates to the Food Management page.
     */
    @FXML
    private void toFoodManagementPage() {
        System.out.println("Navigating to Food Management Page");
        Application.switchScene("food-management-view.fxml");
        // TODO: Finish food management page
    }

    /**
     * Navigates to the Enclosure Management page.
     */
    @FXML
    private void toEnclosureManagementPage() {
        System.out.println("Navigating to Enclosure Management Page");
        SceneContext.selectedEnclosure = worker.getEnclosure();
        Application.switchScene("enclosure-menu-view.fxml");
    }

    /**
     * Populates the UI with the given worker's information.
     * @param worker The Worker to display.
     */
    public void setWorker(Worker worker) {
        worker = Session.getLoggedInWorker();
        workerUsernameLabel.setText(worker.getUsername());
        workerFullNameLabel.setText(worker.getFullName());
        workerEmailLabel.setText(worker.getEmail());
        workerStatusLabel.setText(worker.getStatus().name());
        workerSalaryLabel.setText(worker.getSalary() + " €");

        if (worker.getEnclosure() != null) {
            enclosureLabel1.setText(worker.getEnclosure().getName());
            enclosureLabel2.setText(worker.getEnclosure().getZone());

            List<Schedule> schedules = new ScheduleRepository().getSchedulesByEnclosureId(worker.getEnclosure().getId());
            scheduleViewList.getItems().setAll(schedules);
        } else {
            enclosureLabel1.setText("N/A");
            enclosureLabel2.setText("N/A");
            scheduleViewList.getItems().clear();
        }
    }
}

