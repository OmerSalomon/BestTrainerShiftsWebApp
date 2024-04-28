package controllers;

import services.TrainerService;
import entities.WTrainer;
import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller class for managing trainer-related functionalities.
 */
@Controller
@RequestMapping("/trainermanager")
public class TrainerController {

    // Injects the TrainerService to handle trainer-related operations
    @Autowired
    private TrainerService trainerService;

    /**
     * Displays the trainer dashboard with all trainers associated with the current user.
     * @param session HttpSession to ensure user is logged in.
     * @param model Model to pass attributes to the view.
     * @return the trainer dashboard view or the error page if an exception occurs.
     */
    @GetMapping("/trainer-dashboard")
    public String showTrainers(HttpSession session, Model model) {
        try {
            List<WTrainer> trainers = trainerService.getAllTrainersByUser(getUserFromSession(session));
            model.addAttribute("trainers", trainers);
            return "trainer-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error retrieving trainers: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Handles the addition of a new trainer to the system.
     * @param request HttpServletRequest to extract request parameters for trainer details.
     * @param model Model to pass attributes to the view.
     * @param session HttpSession to ensure user is logged in.
     * @return Redirects to the trainer dashboard upon success or returns to the error page.
     */
    @PostMapping("/addTrainer")
    public String addTrainer(HttpServletRequest request, Model model,
                             @RequestParam String name,
                             @RequestParam(defaultValue = "false") boolean isManager,
                             HttpSession session) {
        try {
            WTrainer trainer = new WTrainer();
            trainer.setName(name);
            trainer.setIsManager(isManager);

            // Handling the availability setup based on the request parameters
            StringBuilder availability = new StringBuilder("000000000000000000000"); // 3 shifts * 7 days
            String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            for (int day = 0; day < days.length; day++) {
                for (int shift = 0; shift < 3; shift++) {
                    String parameterName = "availability[" + day + "][" + shift + "]";
                    boolean isChecked = request.getParameter(parameterName) != null;
                    availability.setCharAt(day * 3 + shift, isChecked ? '1' : '0');
                }
            }

            trainer.setAvailabilityString(availability.toString());
            trainerService.addTrainerByUser(getUserFromSession(session), trainer);
            return "redirect:/trainermanager/trainer-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    /**
     * Allows editing or deleting a trainer based on user action.
     * @param trainerId the ID of the trainer to be edited or deleted.
     * @param action the action to be taken (Edit/Delete).
     * @param session HttpSession to ensure user is logged in.
     * @param model Model to pass attributes to the view.
     * @return Redirects to the appropriate view based on the action taken.
     */
    @PostMapping("/selectedTrainer")
    public String editOrDeleteTrainer(@RequestParam(value = "selectedTrainerId", required = false) Integer trainerId, @RequestParam("action") String action, HttpSession session, Model model) {
        if (trainerId == null) {
            model.addAttribute("errorMessage", "No trainers to " + action);
            return "trainer-dashboard";
        }
        try {
            if ("Delete".equals(action)) {
                trainerService.deleteTrainerByTrainerID(trainerId);
            } else if ("Edit".equals(action)) {
                WTrainer trainer = trainerService.getTrainerByTrainerID(trainerId);
                model.addAttribute("trainer", trainer);
                return "editTrainer";
            }
            return "redirect:/trainermanager/trainer-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    // Utility method to extract the user object from session
    private WUser getUserFromSession(HttpSession session) {
        return (WUser) session.getAttribute("user");
    }

    /**
     * Handles updating trainer details in the system.
     * @param request HttpServletRequest to extract trainer details.
     * @param session HttpSession to ensure user is logged in.
     * @param model Model to pass attributes to the view.
     * @return Redirects to the trainer dashboard upon success or returns to the error page.
     */
    @PostMapping("/updateTrainer")
    public String updateTrainer(HttpServletRequest request, HttpSession session, Model model) {
        WUser user = getUserFromSession(session);
        if (user == null) {
            model.addAttribute("errorMessage", "Please login to update trainers.");
            return "login-form";
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            boolean isManager = request.getParameter("isManager") != null;

            WTrainer trainer = trainerService.getTrainerByTrainerID(id);
            if (trainer == null) {
                model.addAttribute("errorMessage", "No trainer found with ID: " + id);
                return "trainer-dashboard";
            }

            trainer.setName(name);
            trainer.setIsManager(isManager);

            // Update availability string
            StringBuilder availability = new StringBuilder("000000000000000000000"); // 3 shifts * 7 days
            String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            for (int day = 0; day < days.length; day++) {
                for (int shift = 0; shift < 3; shift++) {
                    String parameterName = "availability[" + day + "][" + shift + "]";
                    boolean shiftChecked = request.getParameter(parameterName) != null;
                    availability.setCharAt(day * 3 + shift, shiftChecked ? '1' : '0');
                }
            }

            trainer.setAvailabilityString(availability.toString());
            trainerService.updateTrainerByID(id, trainer);
            return "redirect:/trainermanager/trainer-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating trainer: " + e.getMessage());
            return "error";
        }
    }
}
