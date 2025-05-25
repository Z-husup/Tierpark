package com.prog.tierpark.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnimalFood {
    private Long id;
    private String name;
    private int weight;
    private LocalDate deliveryDate;
    private LocalDate expirationDate;
    private String storageCondition;
    private double quantity;
}

