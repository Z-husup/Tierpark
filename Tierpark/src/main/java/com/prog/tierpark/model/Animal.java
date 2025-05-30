package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.AnimalGender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Animal {
    private Long id;

    private String name;

    private Species species;
    private LocalDate dateOfBirth;
    private LocalDate arrivalDate;
    private int age;
    private AnimalGender gender;
    private int size;
    private int weight;
    private AnimalStatus status;

    private Enclosure enclosure;
}

