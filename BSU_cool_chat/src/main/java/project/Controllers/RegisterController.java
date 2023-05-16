package project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.dao.User.UserService;
import project.models.User;

@Controller
public class RegisterController {
    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/enter")
    public String GreetingPage(Model model) {
        model.addAttribute("user", new User());
        return "registration/login_page";
    }

    @PostMapping("/log_in")
    public String LogIn(@ModelAttribute("user") User user) {
        var id = userService.getUserId(user.getLogin(), user.getPassword());
        return id.map(integer -> "redirect:/chats/" + integer).orElse("redirect:/enter");
    }
}
