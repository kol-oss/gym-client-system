package com.epam.gym.dao;

import com.epam.gym.model.Training;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrainingDao extends GenericDao<UUID, Training> {
}
