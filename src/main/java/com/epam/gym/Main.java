package com.epam.gym;

import com.epam.gym.config.AppConfig;
import com.epam.gym.controller.GymController;
import com.epam.gym.model.Trainee;
import com.epam.gym.model.Trainer;
import com.epam.gym.model.Training;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            GymController gymController = context.getBean(GymController.class);

            Trainee trainee = new Trainee();
            trainee.setFirstName("John");
            trainee.setLastName("Doe");

            // John.Doe and John.Doe1
            gymController.addTrainee(trainee);
            gymController.addTrainee(trainee);

            Trainer trainer = new Trainer();
            trainer.setFirstName("Alan");
            trainer.setLastName("Doe");

            // Alan.Doe and Alan.Doe1
            gymController.addTrainer(trainer);
            gymController.addTrainer(trainer);

            Training training = new Training();
            training.setName("Training");
            training.setTraineeId(trainee.getId());
            training.setTrainerId(trainer.getId());

            gymController.addTraining(training);
        }
    }
}
