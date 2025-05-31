package com.prog.tierpark.service;

import com.prog.tierpark.model.Enclosure;
import com.prog.tierpark.model.Worker;
import com.prog.tierpark.repository.EnclosureRepository;
import com.prog.tierpark.repository.WorkerRepository;

import java.util.List;

/**
 * Service class for worker login, registration, update, and enclosure fetching.
 */
public class WorkerService {

    private final WorkerRepository workerRepository = new WorkerRepository();
    private final EnclosureRepository enclosureRepository = new EnclosureRepository();

    /**
     * Attempts to log in a worker by verifying username and password.
     * @param username Worker’s username.
     * @param password Worker’s password.
     * @return Worker object if credentials are valid; otherwise null.
     */
    public Worker login(String username, String password) {
        Worker worker = workerRepository.getWorkerByUsername(username);
        return (worker != null && worker.getPassword().equals(password)) ? worker : null;
    }

    /**
     * Registers a new worker after validating username uniqueness.
     * @param worker Worker object to register.
     * @return true if registration is successful; otherwise false.
     */
    public boolean register(Worker worker) {
        if (workerRepository.getWorkerByUsername(worker.getUsername()) != null) {
            System.out.println("❌ Username already exists.");
            return false;
        }
        return workerRepository.addWorker(worker);
    }

    /**
     * Updates an existing worker's data.
     * @param worker Worker object with updated data.
     * @return true if update was successful; otherwise false.
     */
    public boolean updateWorker(Worker worker) {
        return workerRepository.updateWorker(worker);
    }

    /**
     * Fetches all available enclosures.
     * @return List of Enclosures.
     */
    public List<Enclosure> getAllEnclosures() {
        return enclosureRepository.getAllEnclosures();
    }
}

