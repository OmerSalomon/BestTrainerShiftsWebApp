package dal;

import entities.WSector;
import entities.WUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of SectorDao interface using Hibernate for data access.
 * This class provides methods to interact with the database to perform CRUD operations on sectors.
 */
@Repository
public class SectorHibernateDao implements SectorDao {

    private SessionFactory factory;

    /**
     * Constructor to initialize the Hibernate SessionFactory.
     * It configures the SessionFactory using the hibernate.cfg.xml file and adds the WSector class as an annotated class.
     */
    public SectorHibernateDao() {
        // Configure SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(WSector.class)
                .buildSessionFactory();
    }

    /**
     * Retrieves all sectors associated with a specific user from the database.
     *
     * @param user The user whose sectors are to be retrieved.
     * @return A list of sectors associated with the given user.
     */

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

    /**
     * Saves a sector to the database.
     *
     * @param sector The sector to be saved.
     */
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

    /**
     * Updates a sector identified by its ID with new sector information in the database.
     *
     * @param sectorId The ID of the sector to be updated.
     * @param sector   The updated sector information.
     */
    @Override
    public void updateSectorBySectorID(int sectorId, WSector sector) {
        save(sector); // Since saveOrUpdate is used in save(), it can also handle updates.
    }

    /**
     * Deletes a sector identified by its ID from the database.
     *
     * @param sectorId The ID of the sector to be deleted.
     */
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

    /**
     * Retrieves a sector by its ID from the database.
     *
     * @param sectorId The ID of the sector to be retrieved.
     * @return The sector associated with the given ID.
     */
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
