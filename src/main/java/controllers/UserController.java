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

/**
 * Controller responsible for handling user login and registration.
 */
@Controller
public class UserController {

    // Injects the UserService to handle user-related operations
    @Autowired
    private UserService userService;

    /**
     * Displays the login form or redirects to the home page if the user is already logged in.
     * @param session HttpSession used to check if the user is already logged in.
     * @return The login form or home page depending on the user's login status.
     */
    @RequestMapping("/")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "home";
        }
        return "login-form";
    }

    /**
     * Processes the login attempt and sets user session on successful authentication.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param model Model to pass attributes to the view.
     * @param session HttpSession to manage user session after login.
     * @return Redirects to the home page on successful login, or shows the login form with an error message.
     */
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

    /**
     * Displays the signup form for new user registration.
     * @param model Model to pass attributes to the view.
     * @return The signup form view.
     */
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        return "signup-form";
    }

    /**
     * Processes user registration and redirects to the login form if successful.
     * @param username The username entered by the new user.
     * @param password The password entered by the new user.
     * @param model Model to pass attributes to the view.
     * @return Redirects to the login page or shows an error if the signup fails.
     */
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
