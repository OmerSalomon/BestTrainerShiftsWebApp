package services;

import dal.TrainerDao;
import entities.WTrainer;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private UserService userService; // Use UserService instead of UserDao


    @PostConstruct
    public void onStartup() {
        System.out.println("Container started.");
    }

    @PreDestroy
    public void onShutdown() {
        System.out.println("Container is shutting down.");
    }



    public List<WTrainer> getAllTrainersByUser(WUser user) throws Exception {
        return trainerDao.getAllTrainersByUser(user);
    }

    public void addTrainerByUser(WUser user, WTrainer trainer) throws Exception {
        trainer.setUser(user); // Set the User object on Trainer instead of setting userId
        trainerDao.save(trainer);
    }

    public void updateTrainerByID(int id, WTrainer trainer) throws Exception {
        WTrainer existingTrainer = trainerDao.getTrainerByTrainerID(id);
        if (existingTrainer == null) {
            throw new TrainerNotFoundException("Cannot update trainer with ID " + trainer.getId() + ". It is not found.");
        }
        trainerDao.updateTrainerByTrainerID(id, trainer);
    }

    public void deleteTrainerByTrainerID(int trainerId) throws Exception {
        WTrainer trainer = trainerDao.getTrainerByTrainerID(trainerId);
        if (trainer == null) {
            throw new TrainerNotFoundException("Trainer with ID " + trainerId + " was not found.");
        }
        trainerDao.deleteTrainerByTrainerID(trainerId);
    }

    public WTrainer getTrainerByTrainerID(int trainerId) throws Exception {
        WTrainer trainer = trainerDao.getTrainerByTrainerID(trainerId);
        if (trainer == null) {
            throw new TrainerNotFoundException("Trainer with ID " + trainerId + " does not exist.");
        }
        return trainer;
    }
}
