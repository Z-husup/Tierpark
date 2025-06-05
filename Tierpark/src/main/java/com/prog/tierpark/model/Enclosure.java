package com.prog.tierpark.model;

import javafx.concurrent.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Represents an animal enclosure in the zoo.
 */
@Data
@AllArgsConstructor
public class Enclosure {

    /** Unique identifier of the enclosure. */
    private Long id;

    /** Name of the enclosure. */
    private String name;

    /** Zone where the enclosure is located. */
    private String zone;

    /** Current operational status of the enclosure (e.g., Active, Under Maintenance). */
    private String status;

    /** Type of enclosure (e.g., Indoor, Outdoor). */
    private String type;

    /** Maximum capacity of animals in the enclosure. */
    private int capacity;

    /** Description of the enclosure. */
    private String description;

    /** Current condition of the enclosure (e.g., Excellent, Poor). */
    private String condition;

    /** Area of the enclosure (e.g., in square meters). */
    private String area;

    /** Workers assigned to this enclosure. */
    private List<Worker> workers;

    /** Animals housed in this enclosure. */
    private List<Animal> animals;

    /** Schedules associated with this enclosure. */
    private List<Schedule> schedules;

    /**
     * Convenience constructor without the workers/animals/schedules list.
     */
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

    /**
     * Returns a string summary of the enclosure.
     */
    @Override
    public String toString() {
        return name + " (" + type + "," + status + ")";
    }
}

