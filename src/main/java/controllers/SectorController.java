package controllers;

import entities.WSector;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.SectorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/sectormanager")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @GetMapping("/sector-dashboard")
    public String showSectors(HttpSession session, Model model) {
        WUser user = (WUser) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            List<WSector> sectors = sectorService.getAllSectorsByUser(user);
            model.addAttribute("sectors", sectors);
            return "sector-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error retrieving sectors: " + e.getMessage());
            return "error";
        }

    }

    @PostMapping("/addSector")
    public String addSector(HttpSession session, Model model,
                            @RequestParam String name,
                            @RequestParam int requiredNumberOfTrainers,
                            @RequestParam(defaultValue = "false") boolean isManager) {
        WUser user = getUserFromSession(session);
        if (user == null) {
            return "redirect:/login";
        }
        try {
            WSector sector = new WSector(name, requiredNumberOfTrainers, isManager);
            sectorService.addSectorByUser(user, sector);
            return "redirect:/sectormanager/sector-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/selectedSector")
    public String editOrDeleteSector(@RequestParam(value = "selectedSectorId", required = false) Integer sectorId, @RequestParam("action") String action, HttpSession session, Model model) {
        if (sectorId == null) {
            model.addAttribute("errorMessage", "No sectors to " + action);
            return "sector-dashboard";
        }
        try {
            if ("Delete".equals(action)) {
                sectorService.deleteSectorBySectorID(sectorId);
            } else if ("Edit".equals(action)) {
                WSector sector = sectorService.getSectorBySectorID(sectorId);
                model.addAttribute("sector", sector);
                return "edit-sector";
            }
            return "redirect:/sectormanager/sector-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    private WUser getUserFromSession(HttpSession session) {
        return (WUser) session.getAttribute("user");
    }

    @PostMapping("/updateSector")
    public String updateSector(HttpServletRequest request, HttpSession session, Model model) {
        WUser user = (WUser) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorMessage", "Please login to update sectors.");
            return "login-form";
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int requiredNumberOfTrainers = Integer.parseInt(request.getParameter("requiredNumberOfTrainers"));

            WSector sector = sectorService.getSectorBySectorID(id);
            if (sector == null) {
                model.addAttribute("errorMessage", "No sector found with ID: " + id);
                return "sector-dashboard";
            }

            sector.setName(name);
            sector.setRequiredNumberOfTrainers(requiredNumberOfTrainers);

            sectorService.updateSectorBySectorID(id, sector);

            return "redirect:/sectormanager/sector-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating sector: " + e.getMessage());
            return "error";
        }
    }
}
