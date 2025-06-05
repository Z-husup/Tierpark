package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MedicalHistory {

    private int id;

    private int animalId;

    private LocalDate date;
    private String description;
}

