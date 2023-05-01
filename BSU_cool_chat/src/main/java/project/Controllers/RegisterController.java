package project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.dao.User.UserDAO;
import project.models.User;

@Controller
public class RegisterController {
    private UserDAO userDAO;

    @Autowired
    public RegisterController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/enter")
    public String GreetingPage(Model model) {
        model.addAttribute("user", new User());
        return "registration/register_page";
    }

    @PostMapping("/log_in")
    public String LogIn(@ModelAttribute("user") User user) {
        var id = userDAO.getUserId(user.getLogin(), user.getPassword());
        return id.map(integer -> "redirect:/users/" + integer).orElse("redirect:/enter");
    }
}
