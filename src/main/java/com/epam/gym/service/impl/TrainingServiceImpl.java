package com.epam.gym.service.impl;

import com.epam.gym.dao.Dao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private Dao<UUID, Training> trainingDao;

    @Autowired
    private Dao<UUID, Trainer> trainerDao;

    @Autowired
    private Dao<UUID, Trainee> traineeDao;

    @Override
    public List<Training> findAllTrainings() {
        return trainingDao.findAll();
    }

    @Override
    public Training findTrainingById(UUID id) {
        Optional<Training> training = trainingDao.findById(id);
        return training
                .orElseThrow(() -> new NotFoundException("Training with id " + id + " not found so it can not be selected"));
    }

    @Override
    public Training createTraining(Training training) {
        UUID trainerId = training.getTrainerId();
        trainerDao.findById(trainerId)
                .orElseThrow(() -> new NotFoundException("Trainer with id " + trainerId + " not found so training can not be created"));

        UUID traineeId = training.getTraineeId();
        traineeDao.findById(traineeId)
                .orElseThrow(() -> new NotFoundException("Trainee with id " + traineeId + " not found so training can not be created"));

        UUID trainingId = UUID.randomUUID();
        training.setId(trainingId);

        trainingDao.insert(trainingId, training);

        return training;
    }
}
