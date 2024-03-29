package project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.config.databases.DatabaseInitializer;
import project.service.User.UserService;
import project.models.User;
import project.utils.SingletonLogger;

@Controller
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/enter")
    public String GreetingPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("is_login_mistake_occur", false);
        SingletonLogger.getInstance().info("GreetingPage");
        return "registration/login_page.html";
    }

    @PostMapping("/log_in")
    public String LogIn(@ModelAttribute("user") User user,
                        Model model) {
        var id = userService.getUserId(user.getLogin(), user.getPassword());
        if (id.isEmpty()) {
            user.setPassword(null);
            model.addAttribute("user", user);
            model.addAttribute("is_login_mistake_occur", true);
            SingletonLogger.getInstance().info("user_id: " + String.valueOf(user.getId()));
            return "registration/login_page.html";
        }
        SingletonLogger.getInstance().info("user_id: " + String.valueOf(user.getId()));
        return "redirect:/chats/" + id.get();
    }
}
