package services;

import dal.SectorDao;
import entities.WSector;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class SectorService {

    @Autowired
    private SectorDao sectorDao;

    @Autowired
    private UserService userService; // Use UserService to handle operations related to User

    @PostConstruct
    public void onStartup() {
        System.out.println("SectorService started.");
    }

    @PreDestroy
    public void onShutdown() {
        System.out.println("SectorService shutting down.");
    }

    public List<WSector> getAllSectorsByUser(WUser user) throws Exception {
        return sectorDao.getAllSectorsByUser(user);
    }

    public void addSectorByUser(WUser user, WSector sector) throws Exception {
        sector.setUser(user); // Associate the sector with a user
        sectorDao.save(sector);
    }

    public void updateSectorBySectorID(int id, WSector sector) throws Exception {
        WSector existingSector = sectorDao.getSectorBySectorID(id);
        if (existingSector == null) {
            throw new Exception("Cannot update sector with ID " + sector.getId() + ". It is not found.");
        }
        sectorDao.updateSectorBySectorID(id, sector);
    }

    public void deleteSectorBySectorID(int sectorId) throws Exception {
        WSector sector = sectorDao.getSectorBySectorID(sectorId);
        if (sector == null) {
            throw new Exception("Sector with ID " + sectorId + " was not found.");
        }
        sectorDao.deleteSectorBySectorID(sectorId);
    }

    public WSector getSectorBySectorID(int sectorId) throws Exception {
        WSector sector = sectorDao.getSectorBySectorID(sectorId);
        if (sector == null) {
            throw new Exception("Sector with ID " + sectorId + " does not exist.");
        }
        return sector;
    }
}
