package dal;

import entities.WUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserHibernateDao implements UserDao {

    private SessionFactory factory;

    public UserHibernateDao() {
        // Configure SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(WUser.class)
                .buildSessionFactory();
    }

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

    @Override
    public List<WUser> getAll() throws Exception {
        Session session = factory.openSession();
        try {
            return session.createQuery("from WUser", WUser.class).list();
        } finally {
            session.close();
        }
    }

    @Override
    public WUser get(int userId) throws Exception {
        Session session = factory.openSession();
        try {
            return session.get(WUser.class, userId);
        } finally {
            session.close();
        }
    }

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

    // Ensure you close the factory when the application stops
    public void close() {
        if (factory != null) {
            factory.close();
        }
    }
}
