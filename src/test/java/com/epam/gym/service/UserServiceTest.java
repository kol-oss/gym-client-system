package com.epam.gym.service;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.dao.TrainerDao;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.User;
import com.epam.gym.properties.AppProperties;
import com.epam.gym.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final String USERNAME_DELIMITER = ":";
    private static final int MAX_PASSWORD_LENGTH = 10;

    @Mock
    private AppProperties appProperties;

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private TrainerDao trainerDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void givenNewUser_whenPreCreateUser_returnFormatedUser() {
        // Arrange
        final String firstName = "FirstName";
        final String lastName = "LastName";

        User userDto = new Trainee();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);

        when(appProperties.getUsernameDelimiter()).thenReturn(USERNAME_DELIMITER);
        when(appProperties.getMaxPasswordLength()).thenReturn(MAX_PASSWORD_LENGTH);

        // Act
        User result = userService.preCreateUser(userDto);

        // Assert
        assertNotNull(result.getId());
        assertNotNull(result.getPassword());
        assertEquals(MAX_PASSWORD_LENGTH, result.getPassword().length());

        String expectedUsername = firstName + USERNAME_DELIMITER + lastName;
        assertEquals(expectedUsername, result.getUsername());

        verify(appProperties, times(1)).getUsernameDelimiter();
        verify(appProperties, times(1)).getMaxPasswordLength();
    }

    @Test
    public void givenUserWithExistingUsername_whenPreCreateUser_returnFormatedUserWithFixedUsername() {
        // Arrange
        final String firstName = "FirstName";
        final String lastName = "LastName";

        Trainee userDto = new Trainee();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setUsername(firstName + USERNAME_DELIMITER + lastName);

        when(appProperties.getUsernameDelimiter()).thenReturn(USERNAME_DELIMITER);
        when(appProperties.getMaxPasswordLength()).thenReturn(MAX_PASSWORD_LENGTH);
        when(traineeDao.findAll()).thenReturn(List.of(userDto));

        // Act
        User result = userService.preCreateUser(userDto);

        // Assert
        assertNotNull(result.getId());
        assertNotNull(result.getPassword());
        assertEquals(MAX_PASSWORD_LENGTH, result.getPassword().length());

        // sequence number added to the end
        String expectedUsername = firstName + USERNAME_DELIMITER + lastName + "1";
        assertEquals(expectedUsername, result.getUsername());

        verify(appProperties, times(1)).getUsernameDelimiter();
        verify(appProperties, times(1)).getMaxPasswordLength();
    }

    @Test
    public void givenExistingUser_whenPreUpdateUserWithUniqueUsername_returnFormatedUser() {
        // Arrange
        final UUID id = UUID.randomUUID();
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String username = firstName + USERNAME_DELIMITER + lastName;
        final String password = "0123456789";

        Trainer userDto = new Trainer();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setUsername(username);
        userDto.setPassword(password);

        // Act
        User result = userService.preUpdateUser(userDto);

        // Assert
        assertEquals(id, result.getId());
        assertEquals(password, result.getPassword());
        assertEquals(username, result.getUsername());
    }

    @Test
    public void givenExistingUser_whenPreUpdateUserWithNotUniqueUsername_returnFormatedUser() {
        // Arrange
        final UUID id = UUID.randomUUID();
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String username = firstName + USERNAME_DELIMITER + lastName;
        final String password = "0123456789";

        Trainer userDto = new Trainer();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setUsername(username);
        userDto.setPassword(password);

        when(trainerDao.findAll()).thenReturn(List.of(userDto));

        // Act
        User result = userService.preUpdateUser(userDto);

        // Assert
        assertEquals(id, result.getId());
        assertEquals(password, result.getPassword());

        // sequence number added to the end
        String expectedUsername = firstName + USERNAME_DELIMITER + lastName + "1";
        assertEquals(expectedUsername, result.getUsername());
    }
}
