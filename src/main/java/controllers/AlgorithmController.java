package controllers;

import entities.algorithm.Program;
import entities.WSector;
import entities.WTrainer;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.TrainerService;
import services.SectorService;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * The AlgorithmController class is responsible for handling HTTP requests related to the algorithm.
 */
@Controller
@RequestMapping("/algorithmmanager")
public class AlgorithmController {

    @Autowired
    private SectorService sectorService; // Service for managing sectors

    @Autowired
    private TrainerService guardService; // Service for managing guards

    /**
     * Displays the algorithm dashboard.
     * @param session The session object
     * @param model The model object
     * @return The algorithm dashboard view
     */
    @GetMapping("/algorithm-dashboard")
    public String showAlgorithmDashboard(HttpSession session, Model model) {
        WUser user = (WUser) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            List<WSector> WSectors = sectorService.getAllSectorsByUser(user);
            List<WTrainer> WTrainers = guardService.getAllTrainersByUser(user);
            System.out.println("\n\n\n" + WSectors + "\n\n\n" + WTrainers);
            model.addAttribute("sectors", WSectors);
            model.addAttribute("trainers", WTrainers);
            return "algorithm-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error retrieving data: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Executes the algorithm.
     * @param session The session object
     * @param model The model object
     * @return The algorithm results view
     */
    @PostMapping("/execute-algorithm")
    public String executeAlgorithm(HttpSession session, Model model) {
        WUser user = (WUser) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            List<WTrainer> WTrainers = guardService.getAllTrainersByUser(user);
            List<WSector> WSectors = sectorService.getAllSectorsByUser(user);

            WTrainer[] WTrainerArray = null;
            WSector[] WSectorArray = null;
            WTrainerArray = (WTrainer[])WTrainers.toArray(new WTrainer[0]);
            WSectorArray = (WSector[])WSectors.toArray(new WSector[0]);


            int[][] result = Program.runGeneticAlgorithm(WTrainerArray, WSectorArray);

            model.addAttribute("scheduleMatrix", result);
            model.addAttribute("trainers", WTrainers);
            model.addAttribute("sectors", WSectors);

            return "algorithm-results";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to execute algorithm: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Runs the genetic algorithm.
     * @param guards The guards
     * @param sectors The sectors
     * @return The best schedule
     */


}
