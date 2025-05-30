package com.prog.tierpark.controller;

import com.prog.tierpark.model.Schedule;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.repository.ScheduleRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

public class WorkerMenuController {

    private Worker loggedInWorker;

    @FXML
    private ListView scheduleViewList;

    @FXML
    private Text workerUsernameLabel;

    @FXML
    private Text enclosureLabel1;

    @FXML
    private Text workerFullNameLabel;

    @FXML
    private Text workerStatusLabel;

    @FXML
    private Text enclosureLabel2;

    @FXML
    private Text workerEmailLabel;

    @FXML
    private Text workerSalaryLabel;

    @FXML
    public void initialize() {
        setWorker(loggedInWorker);
        // TODO: Optional: populate schedule list or set worker info
    }

    @FXML
    private void toEnclosureListPage() {
        System.out.println("Navigating to Enclosure List Page");
        // TODO: Implement scene switch logic
    }

    @FXML
    private void toFoodManagementPage() {
        System.out.println("Navigating to Food Management Page");
        // TODO: Implement scene switch logic
    }

    @FXML
    private void toEnclosureManagementPage() {
        System.out.println("Navigating to Enclosure Management Page");
        // TODO: Implement scene switch logic
    }

    public void setWorker(Worker worker) {
        this.loggedInWorker = worker;

        workerUsernameLabel.setText(worker.getUsername());
        workerFullNameLabel.setText(worker.getFullName());
        workerEmailLabel.setText(worker.getEmail());
        workerStatusLabel.setText(worker.getStatus().name());
        workerSalaryLabel.setText(worker.getSalary() + " €");

        if (worker.getEnclosure() != null) {
            enclosureLabel1.setText(worker.getEnclosure().getName());
            enclosureLabel2.setText(worker.getEnclosure().getZone());

            ScheduleRepository scheduleRepo = new ScheduleRepository();
            List<Schedule> schedules = scheduleRepo.getSchedulesByEnclosureId(worker.getEnclosure().getId());
            scheduleViewList.getItems().setAll(schedules);
        } else {
            enclosureLabel1.setText("N/A");
            enclosureLabel2.setText("N/A");
            scheduleViewList.getItems().clear();
        }
    }


}
