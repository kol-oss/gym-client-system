package com.epam.gym.service.impl;

import com.epam.gym.dao.Dao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainee;
import com.epam.gym.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Dao<UUID, Trainee> traineeDao;

    @Autowired
    private UserService userService;

    public List<Trainee> findAllTrainees() {
        return traineeDao.findAll();
    }

    @Override
    public Trainee findTraineeById(UUID id) {
        return traineeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainee not found via id " + id + " so it can not be selected"));
    }

    @Override
    public Trainee createTrainee(Trainee trainee) {
        Trainee validated = (Trainee) userService.createUser(trainee);
        traineeDao.insert(validated.getId(), validated);

        logger.info("Trainee with username {} and id {} was created", validated.getUsername(), validated.getId());
        return trainee;
    }

    @Override
    public Trainee updateTrainee(UUID id, Trainee trainee) {
        traineeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainee with id " + id + " not found so it can not be updated"));

        Trainee validated = (Trainee) userService.updateUser(trainee);

        traineeDao.update(id, validated);
        logger.debug("Trainee with username {} and id {} updated his data", trainee.getUsername(), trainee.getId());

        return trainee;
    }

    @Override
    public Trainee deleteTrainee(UUID id) {
        Optional<Trainee> trainee = traineeDao.delete(id);
        return trainee.orElseThrow(() -> new NotFoundException("Trainee not found via id " + id + " so it can not be deleted"));
    }
}
