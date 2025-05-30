package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicalHistory {
    private int id;

    private int animalId;
    private String description;
}

