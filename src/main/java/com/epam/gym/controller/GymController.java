package com.epam.gym.controller;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingType;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GymController {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public Trainee getTrainee(UUID id) {
        return traineeService.findTraineeById(id);
    }

    public Trainee addTrainee(Trainee trainee) {
        return traineeService.createTrainee(trainee);
    }

    public Trainee updateTrainee(UUID id, Trainee trainee) {
        return traineeService.updateTrainee(id, trainee);
    }

    public Trainee removeTrainee(UUID id) {
        return traineeService.deleteTrainee(id);
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.findAllTrainers();
    }

    public Trainer getTrainer(UUID id) {
        return trainerService.findTrainerById(id);
    }

    public Trainer addTrainer(Trainer trainer) {
        return trainerService.createTrainer(trainer);
    }

    public Trainer updateTrainer(UUID id, Trainer trainer) {
        return trainerService.updateTrainer(id, trainer);
    }

    public Training getTraining(UUID id) {
        return trainingService.findTrainingById(id);
    }

    public Training addTraining(Training training) {
        return trainingService.createTraining(training);
    }

    public Training scheduleTraining(String name, TrainingType type, Trainer trainer, Trainee trainee) {
        Training training = new Training();
        training.setName(name);
        training.setType(type);
        training.setTrainerId(trainer.getId());
        training.setTraineeId(trainee.getId());

        return trainingService.createTraining(training);
    }

    public List<Training> getTrainingsForTrainee(Trainee trainee) {
        List<Training> trainings = trainingService.findAllTrainings();
        return trainings.stream()
                .filter(t -> t.getTraineeId() == trainee.getId())
                .toList();
    }
}
