package com.prog.tierpark.model;

import lombok.Data;

@Data
public class AnimalStatus {
    private String healthStatus;
    private String breedingStatus;
    private boolean isDead;

    public AnimalStatus(String healthStatus, String breedingStatus, boolean isDead) {
        this.healthStatus = healthStatus;
        this.breedingStatus = breedingStatus;
        this.isDead = isDead;
    }
}

