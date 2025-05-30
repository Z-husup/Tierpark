package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnimalStatus {
    private Long id;

    private String healthStatus;
    private String breedingStatus;
    private boolean isDead;
}

