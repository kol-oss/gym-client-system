package com.epam.gym.service;

import com.epam.gym.model.Training;

import java.util.UUID;

public interface TrainingService {
    Training findTrainingById(UUID id);

    Training createTraining(Training training);
}
