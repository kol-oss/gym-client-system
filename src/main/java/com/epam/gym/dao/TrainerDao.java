package com.epam.gym.dao;

import com.epam.gym.model.Trainer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrainerDao extends GenericDao<UUID, Trainer> {
}
