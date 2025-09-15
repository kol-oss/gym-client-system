package com.epam.gym.service;

import com.epam.gym.dao.TrainerDao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerService {
    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private UserService userService;

    public List<Trainer> findAllTrainers() {
        return trainerDao.findAll();
    }

    public Trainer findTrainerById(UUID id) {
        Optional<Trainer> trainer = trainerDao.findById(id);
        return trainer
                .orElseThrow(() -> new NotFoundException("Trainer with id " + id + " not found"));
    }

    public Trainer createTrainer(Trainer trainer) {
        Trainer validated = (Trainer) userService.createUser(trainer);
        trainerDao.insert(validated.getId(), validated);

        return trainer;
    }

    public Trainer updateTrainer(UUID id, Trainer trainer) {
        trainerDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer with id " + id + " not found"));

        trainerDao.update(id, trainer);
        return trainer;
    }
}
