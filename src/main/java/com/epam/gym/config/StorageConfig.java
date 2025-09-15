package com.epam.gym.config;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.storage.DataStorage;
import com.epam.gym.storage.JsonDataStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.UUID;

@Configuration
@PropertySource("classpath:application.properties")
public class StorageConfig {
    @Value("${app.data.storage.location}")
    private String externalLocation;

    @Value("${app.data.storage.save-updates}")
    private boolean saveUpdates;

    private <K, T> DataStorage<K, T> initStorage(Class<K> key, Class<T> type) {
        JsonDataStorage<K, T> storage = new JsonDataStorage<>(key, type);
        storage.setExternalStorage(externalLocation, saveUpdates);
        return storage;
    }

    @Bean
    public DataStorage<UUID, Trainee> traineeStorage() {
        return initStorage(UUID.class, Trainee.class);
    }

    @Bean
    public DataStorage<UUID, Trainer> trainerStorage() {
        return initStorage(UUID.class, Trainer.class);
    }

    @Bean
    public DataStorage<UUID, Training> trainingStorage() {
        return initStorage(UUID.class, Training.class);
    }
}
