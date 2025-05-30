package com.prog.tierpark.service;

import com.prog.tierpark.model.Worker;
import com.prog.tierpark.repository.WorkerRepository;

public class WorkerService {

    private final WorkerRepository workerRepository = new WorkerRepository();

    /**
     * Login by verifying username and password.
     */
    public Worker login(String username, String password) {
        Worker worker = workerRepository.getWorkerByUsername(username);
        if (worker != null && worker.getPassword().equals(password)) {
            return worker;
        }
        return null;
    }

    /**
     * Registers a new worker with default fields.
     */
    public boolean register(Worker worker) {
        if (workerRepository.getWorkerByUsername(worker.getUsername()) != null) {
            System.out.println("❌ Username already exists.");
            return false;
        }
        return workerRepository.addWorker(worker);
    }
}

