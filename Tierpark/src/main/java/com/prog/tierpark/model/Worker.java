package com.prog.tierpark.model;

import java.time.LocalDate;

public class Worker {
    private Long id;
    private String firstName;
    private String familyName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private int age;
    private String gender;
    private LocalDate hireDate;
    private Schedule schedule;
    private WorkerStatus status;
    private int salary;
    private WorkerSpecialization specialization;
}

