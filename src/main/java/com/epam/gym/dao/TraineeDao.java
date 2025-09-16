package com.epam.gym.dao;

import com.epam.gym.model.Trainee;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TraineeDao extends GenericDao<UUID, Trainee> {
}
