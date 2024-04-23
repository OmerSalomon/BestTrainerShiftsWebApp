package dal;

import entities.WTrainer;
import entities.WUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainerHibernateDao implements TrainerDao {

    private SessionFactory factory;

    public TrainerHibernateDao() {
        // Configure SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(WTrainer.class)
                .buildSessionFactory();
    }

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

    @Override
    public void addTrainerToUser(WUser user, WTrainer trainer) {
        trainer.setUser(user);
        save(trainer);
    }

    @Override
    public void updateTrainerByTrainerID(int trainerId, WTrainer trainer) {
        save(trainer); // Since saveOrUpdate is used in save(), it can also handle updates.
    }

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

    // Ensure you close the factory when the application stops
    public void close() {
        if (factory != null) {
            factory.close();
        }
    }
}
