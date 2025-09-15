package com.epam.gym.dao;

import com.epam.gym.model.Trainee;
import com.epam.gym.storage.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TraineeDao extends GenericDao<UUID, Trainee> {
    @Autowired
    public void setStorage(DataStorage<UUID, Trainee> storage) {
        super.storage = storage;
    }
}
