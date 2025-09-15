package com.epam.gym.dao;

import com.epam.gym.model.Training;
import com.epam.gym.storage.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrainingDao extends GenericDao<UUID, Training> {
    @Autowired
    public void setStorage(DataStorage<UUID, Training> storage) {
        super.storage = storage;
    }
}
