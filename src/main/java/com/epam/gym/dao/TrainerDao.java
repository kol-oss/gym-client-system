package com.epam.gym.dao;

import com.epam.gym.model.Trainer;
import com.epam.gym.storage.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrainerDao extends GenericDao<UUID, Trainer> {
    @Autowired
    public void setStorage(DataStorage<UUID, Trainer> storage) {
        super.storage = storage;
    }
}
