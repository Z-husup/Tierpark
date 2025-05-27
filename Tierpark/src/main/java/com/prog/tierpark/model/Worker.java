package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.WorkerSpecialization;
import com.prog.tierpark.model.enums.WorkerStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Worker {
    private Long id;

    private String username;
    private String password;

    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private LocalDate hireDate;
    private WorkerStatus status;
    private int salary;
    private WorkerSpecialization specialization;

    private Enclosure enclosure;
}

