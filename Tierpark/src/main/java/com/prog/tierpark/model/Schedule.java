package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Schedule {
    private Long id;

    private String description;

    private ScheduleType scheduleType;
    private LocalDateTime startingTime;

    @Override
    public String toString() {
        return description + " (" + scheduleType + ") - " + startingTime;
    }
}

