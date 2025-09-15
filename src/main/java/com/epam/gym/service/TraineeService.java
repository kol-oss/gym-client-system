package com.epam.gym.service;

import com.epam.gym.model.Trainee;

import java.util.UUID;

public interface TraineeService {
    Trainee findTraineeById(UUID id);

    Trainee createTrainee(Trainee trainee);

    Trainee updateTrainee(UUID id, Trainee trainee);

    Trainee deleteTrainee(UUID id);
}
