package com.epam.gym.service;

import com.epam.gym.model.Training;

import java.util.List;
import java.util.UUID;

public interface TrainingService {
    List<Training> findAllTrainings();

    Training findTrainingById(UUID id);

    Training createTraining(Training training);
}
