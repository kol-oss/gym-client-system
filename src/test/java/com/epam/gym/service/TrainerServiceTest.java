package com.epam.gym.service;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
    @Mock
    private TrainerDao trainerDao;

    @Mock
    private UserService userService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    public void givenExistingTrainer_whenFindAllTrainers_thenReturnAllTrainers() {
        // Arrange
        Trainer trainer = new Trainer();
        trainer.setId(UUID.randomUUID());
        trainer.setFirstName("FirstName");
        trainer.setLastName("LastName");

        List<Trainer> expected = List.of(trainer);
        when(trainerDao.findAll()).thenReturn(List.of(trainer));

        // Act
        List<Trainer> result = trainerService.findAllTrainers();

        // Assert
        assertEquals(expected, result);
        verify(trainerDao, times(1)).findAll();
    }

    @Test
    public void givenExistingTrainer_whenFindTrainerById_thenReturnTrainer() {
        // Arrange
        final UUID id = UUID.randomUUID();

        Trainer trainer = new Trainer();
        trainer.setId(id);
        trainer.setFirstName("FirstName");
        trainer.setLastName("LastName");

        when(trainerDao.findById(id)).thenReturn(Optional.of(trainer));

        // Act
        Trainer result = trainerService.findTrainerById(id);

        // Assert
        assertEquals(trainer, result);
        verify(trainerDao, times(1)).findById(id);
    }

    @Test
    public void givenNotExistingTrainer_whenFindTrainerById_thenThrowNotFound() {
        // Arrange
        final UUID wrongId = UUID.randomUUID();

        when(trainerDao.findById(wrongId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> trainerService.findTrainerById(wrongId));
        verify(trainerDao, times(1)).findById(wrongId);
    }

    @Test
    public void givenNewTrainer_whenCreateTrainer_thenReturnCreatedTrainer() {
        // Arrange
        Trainer trainer = new Trainer();
        trainer.setFirstName("FirstName");
        trainer.setLastName("LastName");

        Trainer formatted = new Trainer();
        formatted.setId(UUID.randomUUID());
        formatted.setFirstName("FirstName");
        formatted.setLastName("LastName");
        formatted.setUsername("FirstName.LastName");
        formatted.setPassword("0123456789");

        when(userService.preCreateUser(trainer)).thenReturn(formatted);

        // Act
        Trainer result = trainerService.createTrainer(trainer);

        // Assert
        assertEquals(formatted, result);
        verify(userService, times(1)).preCreateUser(trainer);
        verify(trainerDao, times(1)).insert(formatted.getId(), formatted);
    }

    @Test
    public void givenExistingTrainer_whenUpdateTrainer_thenReturnUpdatedTrainer() {
        // Arrange
        final UUID id = UUID.randomUUID();

        Trainer trainer = new Trainer();
        trainer.setId(id);
        trainer.setFirstName("FirstName");
        trainer.setLastName("LastName");
        trainer.setUsername("FirstName.LastName");
        trainer.setPassword("0123456789");

        Trainer updated = new Trainer();
        updated.setId(id);
        updated.setFirstName("FirstName");
        updated.setLastName("LastName");
        updated.setUsername("FirstName.LastName");
        updated.setPassword("9876543210"); // password changed

        when(trainerDao.findById(id)).thenReturn(Optional.of(trainer));
        when(userService.preUpdateUser(updated)).thenReturn(updated);

        // Act
        Trainer result = trainerService.updateTrainer(id, updated);

        // Assert
        assertEquals(updated, result);
        verify(trainerDao, times(1)).findById(id);
        verify(userService, times(1)).preUpdateUser(updated);
        verify(trainerDao, times(1)).update(id, updated);
    }

    @Test
    public void givenNotExistingTrainer_whenUpdateTrainer_thenThrowNotFound() {
        // Arrange
        final UUID id = UUID.randomUUID();
        Trainer updated = new Trainer();
        updated.setId(id);
        updated.setFirstName("FirstName");
        updated.setLastName("LastName");
        updated.setUsername("FirstName.LastName");
        updated.setPassword("9876543210");

        when(trainerDao.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> trainerService.updateTrainer(id, updated));
        verify(trainerDao, times(1)).findById(id);
        verify(userService, never()).preUpdateUser(any());
        verify(trainerDao, never()).update(any(), any());
    }
}
