package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a scheduled activity or event for an enclosure.
 */
@Data
@AllArgsConstructor
public class Schedule {

    /** Unique identifier for the schedule. */
    private Long id;

    /** Description of the scheduled activity. */
    private String description;

    /** Type of schedule (e.g., Feeding, Cleaning, Medical). */
    private ScheduleType scheduleType;

    /** Starting date and time of the scheduled activity. */
    private LocalDateTime startingTime;

    /**
     * Returns a string summary of the schedule.
     */
    @Override
    public String toString() {
        return description + " (" + scheduleType + ") - " + startingTime;
    }
}
