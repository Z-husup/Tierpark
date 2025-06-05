package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents a medical treatment or history entry for an animal.
 */
@Data
@AllArgsConstructor
public class MedicalHistory {

    /** Unique ID of the medical record. */
    private int id;

    /** ID of the animal this record belongs to. */
    private int animalId;

    /** Date when the medical treatment occurred. */
    private LocalDate date;

    /** Description of the medical procedure or condition. */
    private String description;
}
