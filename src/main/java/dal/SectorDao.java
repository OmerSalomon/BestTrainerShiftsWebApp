package dal;

import entities.WSector;
import entities.WUser;

import java.util.List;

/**
 * Interface defining operations for interacting with sectors in the data access layer.
 */
public interface SectorDao {
    /**
     * Retrieves all sectors associated with a specific user.
     *
     * @param user The user whose sectors are to be retrieved.
     * @return A list of sectors associated with the given user.
     * @throws Exception If an error occurs while retrieving sectors.
     */
    List<WSector> getAllSectorsByUser(WUser user) throws Exception;

    /**
     * Saves a sector to the data store.
     *
     * @param sector The sector to be saved.
     * @throws Exception If an error occurs while saving the sector.
     */
    void save(WSector sector) throws Exception;

    /**
     * Updates a sector identified by its ID with new sector information.
     *
     * @param sectorId The ID of the sector to be updated.
     * @param sector   The updated sector information.
     * @throws Exception If an error occurs while updating the sector.
     */
    void updateSectorBySectorID(int sectorId, WSector sector) throws Exception;

    /**
     * Deletes a sector identified by its ID.
     *
     * @param sectorId The ID of the sector to be deleted.
     * @throws Exception If an error occurs while deleting the sector.
     */
    void deleteSectorBySectorID(int sectorId) throws Exception;

    /**
     * Retrieves a sector by its ID.
     *
     * @param sectorId The ID of the sector to be retrieved.
     * @return The sector associated with the given ID.
     * @throws Exception If an error occurs while retrieving the sector.
     */
    WSector getSectorBySectorID(int sectorId) throws Exception;
}
