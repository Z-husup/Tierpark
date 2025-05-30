package com.prog.tierpark.model;

import javafx.concurrent.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Enclosure {
    private Long id;

    private String name;

    private String zone;
    private String status;
    private String type;
    private int capacity;
    private String description;
    private String condition;
    private String area;

    private List<Worker> workers;
    private List<Animal> animals;
    private List<Schedule> schedules;

    public Enclosure(long id, String name, String zone, String status, String type,
                     int capacity, String description, String condition, String area) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.status = status;
        this.type = type;
        this.capacity = capacity;
        this.description = description;
        this.condition = condition;
        this.area = area;
    }
}

