package com.epam.gym.service;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.service.impl.TraineeServiceImpl;
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
public class TraineeServiceTest {
    @Mock
    private TraineeDao traineeDao;

    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    public void givenExistingTrainee_whenFindAllTrainees_thenReturnAllTrainees() {
        // Arrange
        Trainee trainee = new Trainee();
        trainee.setId(UUID.randomUUID());
        trainee.setFirstName("FirstName");
        trainee.setLastName("LastName");

        List<Trainee> expected = List.of(trainee);
        when(traineeDao.findAll()).thenReturn(List.of(trainee));

        // Act
        List<Trainee> result = traineeService.findAllTrainees();

        // Assert
        assertEquals(expected, result);
        verify(traineeDao, times(1)).findAll();
    }

    @Test
    public void givenExistingTrainee_whenFindTraineeById_thenReturnTrainee() {
        // Arrange
        final UUID id = UUID.randomUUID();

        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setFirstName("FirstName");
        trainee.setLastName("LastName");

        when(traineeDao.findById(id)).thenReturn(Optional.of(trainee));

        // Act
        Trainee result = traineeService.findTraineeById(id);

        // Assert
        assertEquals(result, trainee);
        verify(traineeDao, times(1)).findById(any());
    }

    @Test
    public void givenNotExistingTrainee_whenFindTraineeById_thenThrowNotFound() {
        // Arrange
        final UUID wrongId = UUID.randomUUID();

        when(traineeDao.findById(wrongId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> traineeService.findTraineeById(wrongId));
        verify(traineeDao, times(1)).findById(any());
    }

    @Test
    public void givenNewTrainee_whenCreateTrainee_thenReturnCreatedTrainee() {
        // Arrange
        Trainee trainee = new Trainee();
        trainee.setFirstName("FirstName");
        trainee.setLastName("LastName");

        Trainee formatted = new Trainee();
        formatted.setId(UUID.randomUUID());
        formatted.setFirstName("FirstName");
        formatted.setLastName("LastName");
        formatted.setUsername("FirstName.LastName");
        formatted.setPassword("0123456789");

        when(userService.preCreateUser(trainee)).thenReturn(formatted);

        // Act
        Trainee result = traineeService.createTrainee(trainee);

        // Assert
        assertEquals(formatted, result);

        verify(userService, times(1)).preCreateUser(any());
        verify(traineeDao, times(1)).insert(formatted.getId(), formatted);
    }

    @Test
    public void givenExistingTrainee_whenUpdateTrainee_thenReturnUpdatedTrainee() {
        // Arrange
        final UUID id = UUID.randomUUID();

        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setFirstName("FirstName");
        trainee.setLastName("LastName");
        trainee.setUsername("FirstName.LastName");
        trainee.setPassword("0123456789");

        Trainee updated = new Trainee();
        updated.setId(id);
        updated.setFirstName("FirstName");
        updated.setLastName("LastName");
        updated.setUsername("FirstName.LastName");
        updated.setPassword("9876543210"); // password changed

        when(traineeDao.findById(id)).thenReturn(Optional.of(trainee));
        when(userService.preUpdateUser(updated)).thenReturn(updated);

        // Act
        Trainee result = traineeService.updateTrainee(id, updated);

        // Assert
        assertEquals(updated, result);

        verify(traineeDao, times(1)).findById(id);
        verify(userService, times(1)).preUpdateUser(any());
        verify(traineeDao, times(1)).update(id, updated);
    }

    @Test
    public void givenNotExistingTrainee_whenUpdateTrainee_thenThrowNotFound() {
        // Arrange
        final UUID id = UUID.randomUUID();

        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setFirstName("FirstName");
        trainee.setLastName("LastName");
        trainee.setUsername("FirstName.LastName");
        trainee.setPassword("0123456789");

        Trainee updated = new Trainee();
        updated.setId(id);
        updated.setFirstName("FirstName");
        updated.setLastName("LastName");
        updated.setUsername("FirstName.LastName");
        updated.setPassword("9876543210"); // password changed

        // trainee is not registered

        // Act & Assert
        assertThrows(NotFoundException.class, () -> traineeService.updateTrainee(id, updated));

        verify(traineeDao, times(1)).findById(id);
    }

    @Test
    public void givenExistingTrainee_whenDeleteTrainee_thenReturnDeletedTrainee() {
        // Arrange
        final UUID id = UUID.randomUUID();

        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setFirstName("FirstName");
        trainee.setLastName("LastName");
        trainee.setUsername("FirstName.LastName");
        trainee.setPassword("0123456789");

        when(traineeDao.delete(id)).thenReturn(Optional.of(trainee));

        // Act
        Trainee result = traineeService.deleteTrainee(id);

        // Assert
        assertEquals(trainee, result);

        verify(traineeDao, times(1)).delete(id);
    }

    @Test
    public void givenNotExistingTrainee_whenDeleteTrainee_thenThrowNotFound() {
        // Arrange
        final UUID wrongId = UUID.randomUUID();

        when(traineeDao.delete(wrongId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> traineeService.deleteTrainee(wrongId));
        verify(traineeDao, times(1)).delete(wrongId);
    }
}