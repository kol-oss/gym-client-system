package com.epam.gym.service;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.dao.TrainerDao;
import com.epam.gym.dao.TrainingDao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingService {
    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private TraineeDao traineeDao;

    public Training findTrainingById(UUID id) {
        Optional<Training> training = trainingDao.findById(id);
        return training.orElse(null);
    }

    public Training createTraining(Training training) {
        UUID trainerId = training.getTrainerId();
        trainerDao.findById(trainerId)
                .orElseThrow(() -> new NotFoundException("Trainer with id " + trainerId + " not found"));

        UUID traineeId = training.getTraineeId();
        traineeDao.findById(traineeId)
                .orElseThrow(() -> new NotFoundException("Trainee with id " + traineeId + " not found"));

        UUID trainingId = UUID.randomUUID();
        training.setId(trainingId);

        trainingDao.insert(trainingId, training);

        return training;
    }
}
