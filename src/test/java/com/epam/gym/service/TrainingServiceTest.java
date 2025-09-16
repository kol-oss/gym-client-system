package com.epam.gym.service;

import com.epam.gym.dao.Dao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.model.TrainingType;
import com.epam.gym.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    @Mock
    private Dao<UUID, Training> trainingDao;

    @Mock
    private Dao<UUID, Trainer> trainerDao;

    @Mock
    private Dao<UUID, Trainee> traineeDao;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    public void givenExistingTraining_whenFindTrainingById_thenReturnTraining() {
        // Arrange
        UUID trainingId = UUID.randomUUID();
        Training training = new Training();
        training.setId(trainingId);

        when(trainingDao.findById(trainingId)).thenReturn(Optional.of(training));

        // Act
        Training result = trainingService.findTrainingById(trainingId);

        // Assert
        assertEquals(training, result);
        verify(trainingDao, times(1)).findById(trainingId);
    }

    @Test
    public void givenNotExistingTraining_whenFindTrainingById_thenThrowNotFound() {
        // Arrange
        UUID trainingId = UUID.randomUUID();
        when(trainingDao.findById(trainingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> trainingService.findTrainingById(trainingId));
        verify(trainingDao, times(1)).findById(trainingId);
    }

    @Test
    public void givenValidTraining_whenCreateTraining_thenReturnCreatedTraining() {
        // Arrange
        UUID trainerId = UUID.randomUUID();
        UUID traineeId = UUID.randomUUID();

        Training training = new Training();
        training.setTrainerId(trainerId);
        training.setTraineeId(traineeId);
        training.setName("Cardio");
        training.setType(TrainingType.CARDIO_TRAINING);
        training.setDate(LocalDate.now());
        training.setDuration(Duration.ofHours(1));

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);

        when(trainerDao.findById(trainerId)).thenReturn(Optional.of(trainer));
        when(traineeDao.findById(traineeId)).thenReturn(Optional.of(trainee));

        // Act
        Training result = trainingService.createTraining(training);

        // Assert
        assertEquals(training, result);
        assertEquals(training.getId(), result.getId()); // training ID is set
        verify(trainerDao, times(1)).findById(trainerId);
        verify(traineeDao, times(1)).findById(traineeId);
        verify(trainingDao, times(1)).insert(training.getId(), training);
    }

    @Test
    public void givenNonExistingTrainer_whenCreateTraining_thenThrowNotFound() {
        // Arrange
        UUID trainerId = UUID.randomUUID();
        UUID traineeId = UUID.randomUUID();

        Training training = new Training();
        training.setTrainerId(trainerId);
        training.setTraineeId(traineeId);

        when(trainerDao.findById(trainerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> trainingService.createTraining(training));
        verify(trainerDao, times(1)).findById(trainerId);
        verify(traineeDao, never()).findById(any());
        verify(trainingDao, never()).insert(any(), any());
    }

    @Test
    public void givenNonExistingTrainee_whenCreateTraining_thenThrowNotFound() {
        // Arrange
        UUID trainerId = UUID.randomUUID();
        UUID traineeId = UUID.randomUUID();

        Training training = new Training();
        training.setTrainerId(trainerId);
        training.setTraineeId(traineeId);

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);

        when(trainerDao.findById(trainerId)).thenReturn(Optional.of(trainer));
        when(traineeDao.findById(traineeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> trainingService.createTraining(training));
        verify(trainerDao, times(1)).findById(trainerId);
        verify(traineeDao, times(1)).findById(traineeId);
        verify(trainingDao, never()).insert(any(), any());
    }
}