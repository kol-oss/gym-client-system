package com.epam.gym.service;

import com.epam.gym.dao.TraineeDao;
import com.epam.gym.exception.NotFoundException;
import com.epam.gym.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TraineeService {
    @Autowired
    private TraineeDao traineeDao;

    @Autowired
    private UserService userService;

    public List<Trainee> findAllTrainees() {
        return traineeDao.findAll();
    }

    public Trainee findTraineeById(UUID id) {
        Optional<Trainee> trainee = traineeDao.findById(id);
        return trainee
                .orElseThrow(() -> new NotFoundException("Trainee with id " + id + " not found"));
    }

    public Trainee createTrainee(Trainee trainee) {
        Trainee validated = (Trainee) userService.createUser(trainee);
        traineeDao.insert(validated.getId(), validated);

        return trainee;
    }

    public Trainee updateTrainee(UUID id, Trainee trainee) {
        traineeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainee with id " + id + " not found"));

        traineeDao.update(id, trainee);
        return trainee;
    }

    public Trainee deleteTrainee(UUID id) {
        Optional<Trainee> trainee = traineeDao.delete(id);
        return trainee.orElseThrow(() -> new NotFoundException("Trainee not found via id " + id));
    }
}
