package services;

import dal.TrainerDao;
import entities.WTrainer;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Service class for managing trainer-related operations.
 * This class handles the lifecycle of trainer services and provides methods to manage trainer
 * entities associated with users.
 */
@Service
public class TrainerService {

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private UserService userService; // Use UserService to handle operations related to Users

    /**
     * Method called immediately after the service bean is created and properties set.
     * Logs that the service container has started.
     */
    @PostConstruct
    public void onStartup() {
        System.out.println("Container started.");
    }

    /**
     * Method called just before the service bean is destroyed.
     * Logs that the service container is shutting down.
     */
    @PreDestroy
    public void onShutdown() {
        System.out.println("Container is shutting down.");
    }

    /**
     * Retrieves all trainers associated with a specific user.
     *
     * @param user the user whose trainers are to be retrieved
     * @return a list of {@link WTrainer} objects representing all trainers associated with the specified user
     * @throws Exception if there is an issue retrieving the trainers from the database
     */
    public List<WTrainer> getAllTrainersByUser(WUser user) throws Exception {
        return trainerDao.getAllTrainersByUser(user);
    }

    /**
     * Adds a trainer associated with a specific user to the database.
     *
     * @param user the user associated with the trainer to be added
     * @param trainer the {@link WTrainer} object to add
     * @throws Exception if there is an error saving the trainer to the database
     */
    public void addTrainerByUser(WUser user, WTrainer trainer) throws Exception {
        trainer.setUser(user); // Set the User object on Trainer instead of setting userId
        trainerDao.save(trainer);
    }

    /**
     * Updates a specific trainer by trainer ID.
     *
     * @param id the ID of the trainer to update
     * @param trainer the {@link WTrainer} object containing updated information
     * @throws Exception if the trainer does not exist or if there is an issue updating the trainer in the database
     */
    public void updateTrainerByID(int id, WTrainer trainer) throws Exception {
        WTrainer existingTrainer = trainerDao.getTrainerByTrainerID(id);
        if (existingTrainer == null) {
            throw new TrainerNotFoundException("Cannot update trainer with ID " + trainer.getId() + ". It is not found.");
        }
        trainerDao.updateTrainerByTrainerID(id, trainer);
    }

    /**
     * Deletes a trainer from the database by trainer ID.
     *
     * @param trainerId the ID of the trainer to be deleted
     * @throws Exception if the trainer does not exist or if there is an issue deleting the trainer from the database
     */
    public void deleteTrainerByTrainerID(int trainerId) throws Exception {
        WTrainer trainer = trainerDao.getTrainerByTrainerID(trainerId);
        if (trainer == null) {
            throw new TrainerNotFoundException("Trainer with ID " + trainerId + " was not found.");
        }
        trainerDao.deleteTrainerByTrainerID(trainerId);
    }

    /**
     * Retrieves a trainer by their trainer ID.
     *
     * @param trainerId the ID of the trainer to retrieve
     * @return the {@link WTrainer} object representing the trainer found
     * @throws Exception if the trainer does not exist
     */
    public WTrainer getTrainerByTrainerID(int trainerId) throws Exception {
        WTrainer trainer = trainerDao.getTrainerByTrainerID(trainerId);
        if (trainer == null) {
            throw new TrainerNotFoundException("Trainer with ID " + trainerId + " does not exist.");
        }
        return trainer;
    }
}
