package com.epam.gym.service.impl;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.User;
import com.epam.gym.properties.AppProperties;
import com.epam.gym.storage.DataStorage;
import com.epam.gym.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final String USERNAME_REGEX_TEMPLATE = "%s(\\d+)?$";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AppProperties appProperties;
    private DataStorage<UUID, Trainee> traineeStorage;
    private DataStorage<UUID, Trainer> trainerStorage;

    private static long getUsernameNumber(List<? extends User> users, String username) {
        return users.stream()
                .filter(t -> t.getUsername().matches(String.format(USERNAME_REGEX_TEMPLATE, username)))
                .count();
    }

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setStorages(DataStorage<UUID, Trainee> traineeStorage, DataStorage<UUID, Trainer> trainerStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
    }

    private long getOccurrences(String username) {
        List<Trainee> trainees = traineeStorage.selectAll();
        List<Trainer> trainers = trainerStorage.selectAll();

        return getUsernameNumber(trainees, username) + getUsernameNumber(trainers, username);
    }

    private String formatUsername(String username, long occurrences) {
        if (occurrences == 0)
            return username;
        else {
            String formatted = username + occurrences;
            logger.debug("Username {} was changed into {} due to conflict with existing one", username, formatted);

            return formatted;
        }
    }

    private String createUsername(String firstName, String lastName) {
        String username = firstName + appProperties.getUsernameDelimiter() + lastName;
        long occurrences = getOccurrences(username);

        return formatUsername(username, occurrences);
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

        logger.debug("User {} creation validation passed", username);

        return user;
    }

    protected User updateUser(User user) {
        // Setting username
        String username = user.getUsername();
        long occurrences = getOccurrences(username);

        String validatedUsername = formatUsername(username, occurrences);
        user.setUsername(validatedUsername);

        logger.debug("User {} change validation passed", username);

        return user;
    }
}
