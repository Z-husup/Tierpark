package com.prog.tierpark.model;

import com.prog.tierpark.model.enums.ScheduleType;

import java.time.LocalDateTime;

public class Schedule {
    private Long id;
    private String name;
    private ScheduleType scheduleType;
    private LocalDateTime startingTime;
}

