package com.epam.gym.controller;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingType;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GymSystemTest {
    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private GymController gymSystem;

    private UUID traineeId;
    private UUID trainerId;
    private Trainee trainee;
    private Trainer trainer;
    private Training training;

    @BeforeEach
    void setUp() {
        // Arrange test Trainee
        traineeId = UUID.randomUUID();

        trainee = new Trainee();
        trainee.setId(traineeId);

        // Arrange test Trainer
        trainerId = UUID.randomUUID();

        trainer = new Trainer();
        trainer.setId(trainerId);

        // Arrange test Training
        training = new Training();
        training.setId(UUID.randomUUID());
        training.setTrainerId(trainerId);
        training.setTraineeId(traineeId);
    }

    @Test
    void givenExistingTrainee_whenGetTrainee_thenReturnTrainee() {
        // Arrange
        when(traineeService.findTraineeById(traineeId)).thenReturn(trainee);

        // Act
        Trainee result = gymSystem.getTrainee(traineeId);

        // Assert
        assertEquals(trainee, result);
        verify(traineeService, times(1)).findTraineeById(traineeId);
    }

    @Test
    void givenNewTrainee_whenAddTrainee_thenCallCreateTrainee() {
        // Arrange
        when(traineeService.createTrainee(trainee)).thenReturn(trainee);

        // Act
        Trainee result = gymSystem.addTrainee(trainee);

        // Assert
        assertEquals(trainee, result);
        verify(traineeService, times(1)).createTrainee(trainee);
    }

    @Test
    void givenExistingTrainee_whenUpdateTrainee_thenCallUpdateTrainee() {
        // Arrange
        when(traineeService.updateTrainee(traineeId, trainee)).thenReturn(trainee);

        // Act
        Trainee result = gymSystem.updateTrainee(traineeId, trainee);

        // Assert
        assertEquals(trainee, result);
        verify(traineeService, times(1)).updateTrainee(traineeId, trainee);
    }

    @Test
    void givenExistingTrainee_whenRemoveTrainee_thenCallDeleteTrainee() {
        // Arrange
        when(traineeService.deleteTrainee(traineeId)).thenReturn(trainee);

        // Act
        Trainee result = gymSystem.removeTrainee(traineeId);

        // Assert
        assertEquals(trainee, result);
        verify(traineeService, times(1)).deleteTrainee(traineeId);
    }

    @Test
    void givenExistingTrainer_whenGetTrainer_thenReturnTrainer() {
        // Arrange
        when(trainerService.findTrainerById(trainerId)).thenReturn(trainer);

        // Act
        Trainer result = gymSystem.getTrainer(trainerId);

        // Assert
        assertEquals(trainer, result);
        verify(trainerService, times(1)).findTrainerById(trainerId);
    }

    @Test
    void givenNewTrainer_whenAddTrainer_thenCallCreateTrainer() {
        // Arrange
        when(trainerService.createTrainer(trainer)).thenReturn(trainer);

        // Act
        Trainer result = gymSystem.addTrainer(trainer);

        // Assert
        assertEquals(trainer, result);
        verify(trainerService, times(1)).createTrainer(trainer);
    }

    @Test
    void givenExistingTrainer_whenUpdateTrainer_thenCallUpdateTrainer() {
        // Arrange
        when(trainerService.updateTrainer(trainerId, trainer)).thenReturn(trainer);

        // Act
        Trainer result = gymSystem.updateTrainer(trainerId, trainer);

        // Assert
        assertEquals(trainer, result);
        verify(trainerService, times(1)).updateTrainer(trainerId, trainer);
    }

    @Test
    void givenExistingTraining_whenGetTraining_thenReturnTraining() {
        // Arrange
        when(trainingService.findTrainingById(training.getId())).thenReturn(training);

        // Act
        Training result = gymSystem.getTraining(training.getId());

        // Assert
        assertEquals(training, result);
        verify(trainingService, times(1)).findTrainingById(training.getId());
    }

    @Test
    void givenNewTraining_whenAddTraining_thenCallCreateTraining() {
        // Arrange
        when(trainingService.createTraining(training)).thenReturn(training);

        // Act
        Training result = gymSystem.addTraining(training);

        // Assert
        assertEquals(training, result);
        verify(trainingService, times(1)).createTraining(training);
    }

    @Test
    void givenTrainerAndTrainee_whenScheduleTraining_thenCallCreateTraining() {
        // Arrange
        Training scheduled = new Training();
        scheduled.setName("Cardio");
        scheduled.setType(TrainingType.CARDIO_TRAINING);
        scheduled.setTrainerId(trainerId);
        scheduled.setTraineeId(traineeId);

        when(trainingService.createTraining(any(Training.class))).thenReturn(scheduled);

        // Act
        Training result = gymSystem.scheduleTraining("Cardio", TrainingType.CARDIO_TRAINING, trainer, trainee);

        // Assert
        assertEquals(scheduled, result);
        verify(trainingService, times(1)).createTraining(any(Training.class));
    }

    @Test
    void givenExistingTrainee_whenGetTrainingsForTrainee_thenReturnFilteredTrainings() {
        // Arrange
        when(trainingService.findAllTrainings()).thenReturn(List.of(training));

        // Act
        List<Training> result = gymSystem.getTrainingsForTrainee(trainee);

        // Assert
        assertEquals(1, result.size());
        assertEquals(training, result.getFirst());
        verify(trainingService, times(1)).findAllTrainings();
    }
}
