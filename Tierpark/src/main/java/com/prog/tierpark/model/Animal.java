package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.AnimalGender;
import com.prog.tierpark.model.enums.AnimalGroup;
import com.prog.tierpark.model.enums.HealthStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Animal {
    private Long id;

    private String name;

    private AnimalGroup animalGroup;
    private LocalDate dateOfBirth;
    private LocalDate arrivalDate;
    private int age;
    private AnimalGender gender;
    private int size;
    private int weight;
    private HealthStatus healthStatus;
    private MedicalHistory medicalHistory;

    private Enclosure enclosure;
}

