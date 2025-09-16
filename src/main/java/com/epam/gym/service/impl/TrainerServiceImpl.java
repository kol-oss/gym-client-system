package com.epam.gym.service.impl;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainer;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Trainer> findAllTrainers() {
        return trainerDao.findAll();
    }

    @Override
    public Trainer findTrainerById(UUID id) {
        Optional<Trainer> trainer = trainerDao.findById(id);
        return trainer
                .orElseThrow(() -> new NotFoundException("Trainer with id " + id + " not found"));
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        Trainer validated = (Trainer) userService.preCreateUser(trainer);
        trainerDao.insert(validated.getId(), validated);

        return validated;
    }

    @Override
    public Trainer updateTrainer(UUID id, Trainer trainer) {
        trainerDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer with id " + id + " not found"));

        Trainer validated = (Trainer) userService.preUpdateUser(trainer);

        trainerDao.update(id, validated);
        return trainer;
    }
}
