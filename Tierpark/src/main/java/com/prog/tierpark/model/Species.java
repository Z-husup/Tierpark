package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@AllArgsConstructor
public class Species {
    private Long id;

    private String name;

    private String habitat;
    private String animalClass;
    private String diet;


}

