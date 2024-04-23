package dal;

import entities.WSector;
import entities.WUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SectorHibernateDao implements SectorDao {

    private SessionFactory factory;

    public SectorHibernateDao() {
        // Configure SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(WSector.class)
                .buildSessionFactory();
    }

    @Override
    public List<WSector> getAllSectorsByUser(WUser user) {
        Session session = factory.openSession();
        List<WSector> sectors = null;
        try {
            // Start transaction
            session.beginTransaction();

            // Perform query
            sectors = session.createQuery("FROM WSector s WHERE s.user.id = :userId", WSector.class)
                    .setParameter("userId", user.getId())
                    .getResultList();

            System.out.println(sectors);

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
        return sectors;
    }

    @Override
    public void save(WSector sector) {
        Session session = factory.openSession();
        try {
            // Start transaction
            session.beginTransaction();

            // Save or update sector
            session.saveOrUpdate(sector);

            // Commit transaction
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateSectorBySectorID(int sectorId, WSector sector) {
        save(sector); // Since saveOrUpdate is used in save(), it can also handle updates.
    }

    @Override
    public void deleteSectorBySectorID(int sectorId) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            WSector sector = session.get(WSector.class, sectorId);
            if (sector != null) {
                session.delete(sector);
            }

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public WSector getSectorBySectorID(int sectorId) {
        Session session = factory.openSession();
        WSector sector = null;
        try {
            session.beginTransaction();
            sector = session.get(WSector.class, sectorId);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return sector;
    }

    // Ensure you close the factory when the application stops
    public void close() {
        if (factory != null) {
            factory.close();
        }
    }
}
