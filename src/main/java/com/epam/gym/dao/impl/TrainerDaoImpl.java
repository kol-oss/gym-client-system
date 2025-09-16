package com.epam.gym.dao.impl;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.model.Trainer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrainerDaoImpl extends GenericDao<UUID, Trainer> implements TrainerDao {
}
