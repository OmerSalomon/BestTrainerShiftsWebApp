package services;

import dal.SectorDao;
import entities.WSector;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Service class for managing sector-related operations within a Spring-based application.
 * This class handles operations such as adding, updating, retrieving, and deleting sectors associated with users.
 */
@Service
public class SectorService {

    @Autowired
    private SectorDao sectorDao;  // DAO component for accessing sector data in the database.

    @Autowired
    private UserService userService;  // Service for handling user-related operations.

    /**
     * Initializes the SectorService, typically used for startup logging or setup.
     */
    @PostConstruct
    public void onStartup() {
        System.out.println("SectorService started.");
    }

    /**
     * Cleans up any resources or performs other cleanup tasks before the service is destroyed, typically used for logging.
     */
    @PreDestroy
    public void onShutdown() {
        System.out.println("SectorService shutting down.");
    }

    /**
     * Retrieves all sectors associated with a specific user.
     *
     * @param user the user whose sectors are to be retrieved
     * @return a list of {@link WSector} objects representing the sectors associated with the specified user
     * @throws Exception if there is an error retrieving the sectors from the database
     */
    public List<WSector> getAllSectorsByUser(WUser user) throws Exception {
        return sectorDao.getAllSectorsByUser(user);
    }

    /**
     * Adds a sector to the database associated with a specific user.
     *
     * @param user the user to associate with the sector
     * @param sector the sector to be added
     * @throws Exception if there is an error saving the sector in the database
     */
    public void addSectorByUser(WUser user, WSector sector) throws Exception {
        sector.setUser(user);  // Associate the sector with the user
        sectorDao.save(sector);
    }

    /**
     * Updates a specific sector by its ID.
     *
     * @param id the ID of the sector to update
     * @param sector the sector entity with updated details to apply
     * @throws Exception if the sector is not found or there is an error updating the sector in the database
     */
    public void updateSectorBySectorID(int id, WSector sector) throws Exception {
        WSector existingSector = sectorDao.getSectorBySectorID(id);
        if (existingSector == null) {
            throw new Exception("Cannot update sector with ID " + sector.getId() + ". It is not found.");
        }
        sectorDao.updateSectorBySectorID(id, sector);
    }

    /**
     * Deletes a sector from the database by its ID.
     *
     * @param sectorId the ID of the sector to delete
     * @throws Exception if the sector is not found or there is an error during the deletion process
     */
    public void deleteSectorBySectorID(int sectorId) throws Exception {
        WSector sector = sectorDao.getSectorBySectorID(sectorId);
        if (sector == null) {
            throw new Exception("Sector with ID " + sectorId + " was not found.");
        }
        sectorDao.deleteSectorBySectorID(sectorId);
    }

    /**
     * Retrieves a sector by its ID, if it exists.
     *
     * @param sectorId the ID of the sector to retrieve
     * @return the {@link WSector} found with the specified ID
     * @throws Exception if the sector does not exist
     */
    public WSector getSectorBySectorID(int sectorId) throws Exception {
        WSector sector = sectorDao.getSectorBySectorID(sectorId);
        if (sector == null) {
            throw new Exception("Sector with ID " + sectorId + " does not exist.");
        }
        return sector;
    }
}
