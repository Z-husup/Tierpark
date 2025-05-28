package com.prog.tierpark.model;

import lombok.Data;

@Data
public class AnimalStatus {
    private Long id;

    private String healthStatus;
    private String breedingStatus;
    private boolean isDead;
}

