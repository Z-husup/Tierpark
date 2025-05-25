package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {
    private Long id;
    private String name;
    private ScheduleType scheduleType;
    private LocalDateTime startingTime;
}

