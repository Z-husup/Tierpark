package com.prog.tierpark.model;

import lombok.Data;

@Data
public class MedicalHistory {
    private int id;

    private int animalId;
    private String description;
}

