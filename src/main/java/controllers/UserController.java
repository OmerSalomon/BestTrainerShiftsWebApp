package controllers;

import entities.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.UserNotFoundException;
import services.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
   private UserService userService;


    @RequestMapping("/")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "home";
        }
        return "login-form";
    }
    
    @PostMapping("/login")
    public String processLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model, HttpSession session) {
        try {
            WUser user = userService.login(username, password);
            session.setAttribute("user", user);
            return "home";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "login-form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "error";
        }
    }


    @GetMapping("/signup")
    public String showSignupForm( Model model) {
        return "signup-form";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam("username") String username,
            @RequestParam("password") String password, Model model) {
        try {
        	WUser user = new WUser(username, password);
            userService.addUser(user);
            return "redirect:/";
        } catch (Exception e) {
        	model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "error";
        }
    }

}
