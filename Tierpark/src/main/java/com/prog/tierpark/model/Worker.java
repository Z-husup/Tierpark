package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.WorkerSpecialization;
import com.prog.tierpark.model.enums.WorkerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents a worker or zookeeper assigned to the zoo.
 */
@Data
@AllArgsConstructor
public class Worker {

    /** Unique identifier for the worker. */
    private Long id;

    /** Login username. */
    private String username;

    /** Login password. */
    private String password;

    /** Full name of the worker. */
    private String fullName;

    /** Email address. */
    private String email;

    /** Phone number. */
    private String phoneNumber;

    /** Date of birth. */
    private LocalDate dateOfBirth;

    /** Gender (as a string for simplicity). */
    private String gender;

    /** Date of hiring. */
    private LocalDate hireDate;

    /** Employment status (e.g., Active, On Leave). */
    private WorkerStatus status;

    /** Monthly salary of the worker. */
    private int salary;

    /** Area of specialization (e.g., Vet, Feeder). */
    private WorkerSpecialization specialization;

    /** The enclosure this worker is assigned to. */
    private Enclosure enclosure;

    /**
     * Returns a readable string with the worker's name and role.
     */
    @Override
    public String toString() {
        return fullName + " - " + specialization;
    }
}
