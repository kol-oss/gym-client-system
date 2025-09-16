package com.epam.gym.dao.impl;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.model.Trainee;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TraineeDaoImpl extends GenericDao<UUID, Trainee> implements TraineeDao {
}
