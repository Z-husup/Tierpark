package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents a food item stored for feeding animals.
 */
@Data
@AllArgsConstructor
public class AnimalFood {

    /** Unique identifier of the food entry. */
    private Long id;

    /** Name of the food item. */
    private String name;

    /** Quantity of food items available. */
    private int quantity;

    /** Weight of the food (in kg or grams). */
    private int weight;

    /** Date the food was delivered. */
    private LocalDate deliveryDate;

    /** Expiration date of the food. */
    private LocalDate expirationDate;

    /** Conditions under which the food must be stored. */
    private String storageCondition;
}
