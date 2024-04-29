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

/**
 * Controller for managing sector-related actions within a web application context.
 * This class handles web requests to perform CRUD operations on sectors, manage session attributes, and interact with the sector service.
 */
@Controller
@RequestMapping("/sectormanager")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    /**
     * Displays the sector dashboard page with a list of sectors for the logged-in user.
     * If no user is found in the session, redirects to the login page.
     *
     * @param session the HTTP session containing the user's information
     * @param model the model to pass attributes to the view
     * @return the name of the view to render
     */
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

    /**
     * Handles the submission of a form to add a new sector.
     * If no user is in the session, redirects to the login page.
     *
     * @param session the HTTP session for user verification
     * @param model the model to pass attributes to the view
     * @param name the name of the new sector
     * @param morningShiftSize the size of the morning shift for the new sector
     * @param noonShiftSize the size of the noon shift for the new sector
     * @param eveningShiftSize the size of the evening shift for the new sector
     * @return the redirection path after processing the post request
     */
    @PostMapping("/addSector")
    public String addSector(HttpSession session, Model model,
                            @RequestParam String name,
                            @RequestParam int morningShiftSize,
                            @RequestParam int noonShiftSize,
                            @RequestParam int eveningShiftSize) {
        WUser user = getUserFromSession(session);
        if (user == null) {
            return "redirect:/login";
        }
        try {
            WSector sector = new WSector(name, morningShiftSize, noonShiftSize, eveningShiftSize);
            sectorService.addSectorByUser(user, sector);
            return "redirect:/sectormanager/sector-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    /**
     * Handles the request to either edit or delete a sector based on the action parameter.
     *
     * @param sectorId the ID of the sector to edit or delete
     * @param action the action to perform ("Edit" or "Delete")
     * @param session the HTTP session to verify the user
     * @param model the model to pass attributes to the view
     * @return the view name or redirection path depending on the action
     */
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

    /**
     * Retrieves the logged-in user from the HTTP session.
     *
     * @param session The HTTP session containing user information.
     * @return The logged-in user if present in the session, otherwise returns null.
     */
    private WUser getUserFromSession(HttpSession session) {
        return (WUser) session.getAttribute("user");
    }

    /**
     * Handles the POST request to update a sector.
     * Retrieves user information from the session and checks if the user is logged in.
     * If the user is not logged in, redirects to the login form with an error message.
     * Extracts parameters from the request such as sector ID, name, and shift sizes.
     * Retrieves the sector by its ID and checks if it exists.
     * If the sector does not exist, returns to the sector dashboard with an error message.
     * Updates the sector properties with new values.
     * Updates the sector in the database.
     * Redirects to the sector dashboard after successful update.
     * If an error occurs during the update process, redirects to the error page with an error message.
     *
     * @param request The HTTP servlet request containing the sector update information.
     * @param session The HTTP session containing user information.
     * @param model   The model to which error messages or attributes are added.
     * @return The view to render after processing the update request.
     */
    @PostMapping("/updateSector")
    public String updateSector(HttpServletRequest request, HttpSession session, Model model) {
        WUser user = getUserFromSession(session);
        if (user == null) {
            model.addAttribute("errorMessage", "Please login to update sectors.");
            return "login-form";
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int morningShiftSize = Integer.parseInt(request.getParameter("morningShiftSize"));
            int noonShiftSize = Integer.parseInt(request.getParameter("noonShiftSize"));
            int eveningShiftSize = Integer.parseInt(request.getParameter("eveningShiftSize"));

            WSector sector = sectorService.getSectorBySectorID(id);
            if (sector == null) {
                model.addAttribute("errorMessage", "No sector found with ID: " + id);
                return "sector-dashboard";
            }

            sector.setName(name);
            sector.setMorningShiftSize(morningShiftSize);
            sector.setNoonShiftSize(noonShiftSize);
            sector.setEveningShiftSize(eveningShiftSize);

            sectorService.updateSectorBySectorID(id, sector);

            return "redirect:/sectormanager/sector-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating sector: " + e.getMessage());
            return "error";
        }
    }

}
