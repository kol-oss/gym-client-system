package com.epam.gym.service;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.User;
import com.epam.gym.properties.AppProperties;
import com.epam.gym.storage.DataStorage;
import com.epam.gym.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final String USERNAME_REGEX_TEMPLATE = "%s(\\d+)?$";

    private AppProperties appProperties;

    private DataStorage<UUID, Trainee> traineeStorage;
    private DataStorage<UUID, Trainer> trainerStorage;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setStorages(DataStorage<UUID, Trainee> traineeStorage, DataStorage<UUID, Trainer> trainerStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
    }

    private static long getUsernameNumber(List<? extends User> users, String username) {
        return users.stream()
                .filter(t -> t.getUsername().matches(String.format(USERNAME_REGEX_TEMPLATE, username)))
                .count();
    }

    private String createUsername(String firstName, String lastName) {
        String username = firstName + appProperties.getUsernameDelimiter() + lastName;

        List<Trainee> trainees = traineeStorage.selectAll();
        List<Trainer> trainers = trainerStorage.selectAll();

        long occurrences = getUsernameNumber(trainees, username) + getUsernameNumber(trainers, username);
        if (occurrences == 0)
            return username;
        else
            return username + occurrences;
    }

    protected User createUser(User user) {
        // Setting new id
        UUID userId = UUID.randomUUID();
        user.setId(userId);

        // Setting password
        String password = PasswordUtils.generate(appProperties.getMaxPasswordLength());
        user.setPassword(password);

        // Setting username
        String username = createUsername(user.getFirstName(), user.getLastName());
        user.setUsername(username);

        return user;
    }
}
