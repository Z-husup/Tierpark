package com.prog.tierpark.model;

import lombok.Data;

@Data
public class Species {
    private Long id;

    private String name;

    private String habitat;
    private String animalClass;
    private String diet;
}

