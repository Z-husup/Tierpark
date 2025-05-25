package com.prog.tierpark.model;

import javafx.concurrent.Worker;
import lombok.Data;

import java.util.List;

@Data
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
}

