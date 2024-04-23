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

@Controller
@RequestMapping("/trainermanager")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/trainer-dashboard")
    public String showTrainers(HttpSession session, Model model) {
        try {
            List<WTrainer> trainers = trainerService.getAllTrainersByUser(getUserFromSession(session));
            System.out.println(getUserFromSession(session));
            model.addAttribute("trainers", trainers);
            return "trainer-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error retrieving trainers: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/addTrainer")
    public String addTrainer(HttpServletRequest request, Model model,
                           @RequestParam String name,
                           @RequestParam(defaultValue = "false") boolean isManager,
                           HttpSession session) {
        try {
            WTrainer trainer = new WTrainer();

            trainer.setName(name);

            // Handle the boolean properties directly
            trainer.setIsManager(isManager);

            // Continue with availability string processing
            StringBuilder availability = new StringBuilder("000000000000000000000"); // 3 shifts * 7 days
            String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            for (int day = 0; day < days.length; day++) {
                for (int shift = 0; shift < 3; shift++) {
                    String parameterName = "availability[" + day + "][" + shift + "]";
                    boolean isChecked = request.getParameter(parameterName) != null;
                    if (isChecked) {
                        // If checkbox was checked, set the corresponding position to '1'
                        availability.setCharAt(day * 3 + shift, '1');
                    }
                }
            }

            String availabilityString = availability.toString();
            trainer.setAvailabilityString(availabilityString);

            // Assuming getUserFromSession(session) correctly retrieves the user associated with the session
            trainerService.addTrainerByUser(getUserFromSession(session), trainer);

            return "redirect:/trainermanager/trainer-dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }


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

    private WUser getUserFromSession(HttpSession session) {
        WUser user = (WUser) session.getAttribute("user");
        return user;
    }

    @PostMapping("/updateTrainer")
    public String updateTrainer(HttpServletRequest request, HttpSession session, Model model) {
        WUser user = (WUser) session.getAttribute("user");
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
