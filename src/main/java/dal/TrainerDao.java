package dal;

import entities.WTrainer;
import entities.WUser;

import java.util.List;

public interface TrainerDao {
	    List<WTrainer> getAllTrainersByUser(WUser user) throws Exception;

	    void save(WTrainer trainer) throws Exception;

	    void addTrainerToUser(WUser user, WTrainer trainer) throws Exception;

	    void updateTrainerByTrainerID(int trainerId, WTrainer trainer) throws Exception;

	    void deleteTrainerByTrainerID(int trainerId) throws Exception;

		WTrainer getTrainerByTrainerID(int trainerId) throws Exception;

}