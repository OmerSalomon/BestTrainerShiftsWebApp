package dal;

import entities.WSector;
import entities.WUser;

import java.util.List;

public interface SectorDao {
    List<WSector> getAllSectorsByUser(WUser user) throws Exception;

    void save(WSector sector) throws Exception;

    void updateSectorBySectorID(int sectorId, WSector sector) throws Exception;

    void deleteSectorBySectorID(int sectorId) throws Exception;

    WSector getSectorBySectorID(int sectorId) throws Exception;

}