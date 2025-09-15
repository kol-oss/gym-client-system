package com.epam.gym.service;

import com.epam.gym.model.Trainer;

import java.util.List;
import java.util.UUID;

public interface TrainerService {
    List<Trainer> findAllTrainers();

    Trainer findTrainerById(UUID id);

    Trainer createTrainer(Trainer trainer);

    Trainer updateTrainer(UUID id, Trainer trainer);
}
