package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.AnimalGender;
import com.prog.tierpark.model.enums.AnimalGroup;
import com.prog.tierpark.model.enums.HealthStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents an animal in the zoo.
 */
@Data
@AllArgsConstructor
public class Animal {

    /** Unique identifier of the animal. */
    private Long id;

    /** Name of the animal. */
    private String name;

    /** The group/type the animal belongs to (e.g., mammals, birds). */
    private AnimalGroup animalGroup;

    /** The animal's date of birth. */
    private LocalDate dateOfBirth;

    /** The date the animal arrived at the zoo. */
    private LocalDate arrivalDate;

    /** Age of the animal (in years). */
    private int age;

    /** Gender of the animal. */
    private AnimalGender gender;

    /** Size of the animal (e.g., in cm or other units). */
    private int size;

    /** Weight of the animal (in kg or other units). */
    private int weight;

    /** Current health status of the animal. */
    private HealthStatus healthStatus;

    /** Medical history record of the animal. */
    private MedicalHistory medicalHistory;

    /** Enclosure where the animal is currently located. */
    private Enclosure enclosure;
}


