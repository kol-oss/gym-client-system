package com.epam.gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrainingType {
    STRENGTH_TRAINING("Strength Training"),
    CARDIO_TRAINING("Cardio Training"),
    CIRCUIT_TRAINING("Circuit Training"),
    FUNCTIONAL_TRAINING("Functional Training"),
    ENDURANCE_TRAINING("Strength-Endurance Training"),
    POWER_TRAINING("Power Training"),
    CORE_TRAINING("Core Training");

    private final String name;
}
