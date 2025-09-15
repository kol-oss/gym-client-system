package com.epam.gym.config;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.properties.AppProperties;
import com.epam.gym.storage.DataStorage;
import com.epam.gym.storage.FileDataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class StorageConfig {
    private AppProperties appProperties;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    private <K, T> DataStorage<K, T> initStorage(Class<K> key, Class<T> type) {
        return new FileDataStorage<>(key, type, appProperties.getStorageLocation(), appProperties.isSaveUpdates());
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
