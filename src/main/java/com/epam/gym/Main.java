package com.epam.gym;

import com.epam.gym.config.AppConfig;
import com.epam.gym.model.Trainee;
import com.epam.gym.service.TraineeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            TraineeService traineeService = context.getBean(TraineeService.class);

            Trainee trainee = new Trainee();
            trainee.setFirstName("John");
            trainee.setLastName("Doe");

            traineeService.createTrainee(trainee);
        }
    }
}
