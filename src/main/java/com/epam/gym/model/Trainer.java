package com.epam.gym.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Trainer extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String specialization;
    private TrainingType trainingType;

    private Training training;
}
