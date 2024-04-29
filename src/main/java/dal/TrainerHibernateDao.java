package dal;

import entities.WTrainer;
import entities.WUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of TrainerDao interface using Hibernate for data access.
 * This class provides methods to interact with the database to perform CRUD operations on trainers.
 */
@Repository
public class TrainerHibernateDao implements TrainerDao {

    private SessionFactory factory;

    /**
     * Constructor to initialize the Hibernate SessionFactory.
     * It configures the SessionFactory using the hibernate.cfg.xml file and adds the WTrainer class as an annotated class.
     */
    public TrainerHibernateDao() {
        // Configure SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(WTrainer.class)
                .buildSessionFactory();
    }

    /**
     * Retrieves all trainers associated with a specific user from the database.
     *
     * @param user The user whose trainers are to be retrieved.
     * @return A list of trainers associated with the given user.
     */
    @Override
    public List<WTrainer> getAllTrainersByUser(WUser user) {
        Session session = factory.openSession();
        List<WTrainer> trainers = null;
        try {
            // Start transaction
            session.beginTransaction();

            // Perform query
            trainers = session.createQuery("FROM WTrainer g WHERE g.user.id = :userId", WTrainer.class)
                    .setParameter("userId", user.getId())
                    .getResultList();

            System.out.println(trainers);

            // Commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return trainers;
    }

    /**
     * Saves a trainer to the database.
     *
     * @param trainer The trainer to be saved.
     */
    @Override
    public void save(WTrainer trainer) {
        Session session = factory.openSession();
        try {
            // Start transaction
            session.beginTransaction();

            // Save or update trainer
            session.saveOrUpdate(trainer);

            // Commit transaction
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * Associates a trainer with a user in the database.
     *
     * @param user    The user to whom the trainer is to be associated.
     * @param trainer The trainer to be associated with the user.
     */
    @Override
    public void addTrainerToUser(WUser user, WTrainer trainer) {
        trainer.setUser(user);
        save(trainer);
    }

    /**
     * Updates a trainer identified by its ID with new trainer information in the database.
     *
     * @param trainerId The ID of the trainer to be updated.
     * @param trainer   The updated trainer information.
     */
    @Override
    public void updateTrainerByTrainerID(int trainerId, WTrainer trainer) {
        save(trainer); // Since saveOrUpdate is used in save(), it can also handle updates.
    }

    /**
     * Deletes a trainer identified by its ID from the database.
     *
     * @param trainerId The ID of the trainer to be deleted.
     */
    @Override
    public void deleteTrainerByTrainerID(int trainerId) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            WTrainer trainer = session.get(WTrainer.class, trainerId);
            if (trainer != null) {
                session.delete(trainer);
            }

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * Retrieves a trainer by its ID from the database.
     *
     * @param trainerId The ID of the trainer to be retrieved.
     * @return The trainer associated with the given ID.
     */
    @Override
    public WTrainer getTrainerByTrainerID(int trainerId) {
        Session session = factory.openSession();
        WTrainer trainer = null;
        try {
            session.beginTransaction();
            trainer = session.get(WTrainer.class, trainerId);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return trainer;
    }

    /**
     * Closes the Hibernate SessionFactory when the application stops.
     */
    // Ensure you close the factory when the application stops
    public void close() {
        if (factory != null) {
            factory.close();
        }
    }
}
