package dal;

import entities.WUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of UserDao interface using Hibernate for data access.
 * This class provides methods to interact with the database to perform CRUD operations on users.
 */
@Repository
public class UserHibernateDao implements UserDao {

    private SessionFactory factory;

    /**
     * Constructor to initialize the Hibernate SessionFactory.
     * It configures the SessionFactory using the hibernate.cfg.xml file and adds the WUser class as an annotated class.
     */
    public UserHibernateDao() {
        // Configure SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(WUser.class)
                .buildSessionFactory();
    }

    /**
     * Adds a new user to the database.
     *
     * @param user The user to be added.
     * @throws Exception If an error occurs while adding the user.
     */
    @Override
    public void add(WUser user) throws Exception {
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The user to be updated.
     * @throws Exception If an error occurs while updating the user.
     */
    @Override
    public void update(WUser user) throws Exception {
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param userId The ID of the user to be deleted.
     * @throws Exception If an error occurs while deleting the user.
     */
    @Override
    public void delete(int userId) throws Exception {
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            WUser user = session.get(WUser.class, userId);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     * @throws Exception If an error occurs while retrieving users.
     */
    @Override
    public List<WUser> getAll() throws Exception {
        Session session = factory.openSession();
        try {
            return session.createQuery("from WUser", WUser.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user associated with the given ID.
     * @throws Exception If an error occurs while retrieving the user.
     */
    @Override
    public WUser get(int userId) throws Exception {
        Session session = factory.openSession();
        try {
            return session.get(WUser.class, userId);
        } finally {
            session.close();
        }
    }

    /**
     * Checks if a user with the given password and name exists.
     *
     * @param password The password of the user to check.
     * @param name     The name of the user to check.
     * @return true if a user with the given password and name exists, otherwise false.
     */
    @Override
    public boolean check_user(String password, String name) {
        try {
            List<WUser> users = getAll();
            return users.stream()
                    .anyMatch(user -> user.getUsername().equals(name) && user.getPassword().equals(password));
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a user from the database by their username and password.
     *
     * @param username The username of the user to retrieve.
     * @param password The password of the user to retrieve.
     * @return The user associated with the given username and password.
     * @throws Exception If an error occurs while retrieving the user.
     */
    @Override
    public WUser getUserByUsernameAndPassword(String username, String password) throws Exception {
        Session session = factory.openSession();
        try {
            return session.createQuery("FROM WUser WHERE username = :username AND password = :password", WUser.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        } finally {
            session.close();
        }
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
