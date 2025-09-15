package com.epam.gym.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Training {
    private UUID traineeId;
    private UUID trainerId;

    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Duration trainingDuration;
}
