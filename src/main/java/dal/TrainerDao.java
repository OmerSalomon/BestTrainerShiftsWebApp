package dal;

import entities.WTrainer;
import entities.WUser;

import java.util.List;

/**
 * Interface defining operations for interacting with trainers in the data access layer.
 */
public interface TrainerDao {
	/**
	 * Retrieves all trainers associated with a specific user.
	 *
	 * @param user The user whose trainers are to be retrieved.
	 * @return A list of trainers associated with the given user.
	 * @throws Exception If an error occurs while retrieving trainers.
	 */
	List<WTrainer> getAllTrainersByUser(WUser user) throws Exception;

	/**
	 * Saves a trainer to the data store.
	 *
	 * @param trainer The trainer to be saved.
	 * @throws Exception If an error occurs while saving the trainer.
	 */
	void save(WTrainer trainer) throws Exception;

	/**
	 * Associates a trainer with a user.
	 *
	 * @param user    The user to whom the trainer is to be associated.
	 * @param trainer The trainer to be associated with the user.
	 * @throws Exception If an error occurs while associating the trainer with the user.
	 */
	void addTrainerToUser(WUser user, WTrainer trainer) throws Exception;

	/**
	 * Updates a trainer identified by its ID with new trainer information.
	 *
	 * @param trainerId The ID of the trainer to be updated.
	 * @param trainer   The updated trainer information.
	 * @throws Exception If an error occurs while updating the trainer.
	 */
	void updateTrainerByTrainerID(int trainerId, WTrainer trainer) throws Exception;

	/**
	 * Deletes a trainer identified by its ID.
	 *
	 * @param trainerId The ID of the trainer to be deleted.
	 * @throws Exception If an error occurs while deleting the trainer.
	 */
	void deleteTrainerByTrainerID(int trainerId) throws Exception;

	/**
	 * Retrieves a trainer by its ID.
	 *
	 * @param trainerId The ID of the trainer to be retrieved.
	 * @return The trainer associated with the given ID.
	 * @throws Exception If an error occurs while retrieving the trainer.
	 */
	WTrainer getTrainerByTrainerID(int trainerId) throws Exception;
}
