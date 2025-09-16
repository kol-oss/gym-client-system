package com.epam.gym.config;

import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import com.epam.gym.platform.FilePlatform;
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

    private <K, T> DataStorage<K, T> initStorage(Class<K> key, Class<T> type, FilePlatform filePlatform) {
        FileDataStorage<K, T> storage = new FileDataStorage<>(key, type, appProperties.getStorageLocation(), appProperties.isSaveUpdates());
        storage.setFilePlatform(filePlatform);
        return storage;
    }

    @Bean
    public DataStorage<UUID, Trainee> traineeStorage(FilePlatform filePlatform) {
        return initStorage(UUID.class, Trainee.class, filePlatform);
    }

    @Bean
    public DataStorage<UUID, Trainer> trainerStorage(FilePlatform filePlatform) {
        return initStorage(UUID.class, Trainer.class, filePlatform);
    }

    @Bean
    public DataStorage<UUID, Training> trainingStorage(FilePlatform filePlatform) {
        return initStorage(UUID.class, Training.class, filePlatform);
    }
}
