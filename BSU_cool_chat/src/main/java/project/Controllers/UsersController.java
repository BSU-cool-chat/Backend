package project.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.Exceptions.DuplicateLoginException;
import project.Exceptions.UserNotFoundException;
import project.service.User.UserService;
import project.models.User;
import project.utils.SingletonLogger;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{user_id}/supervise/{another_user_id}")
    public String show(@PathVariable("user_id") int user_id,
                       @PathVariable("another_user_id") int another_user_id,
                       Model model) {
        model.addAttribute("user_id", user_id);
        try {
            model.addAttribute("supervising_user_info", userService.getUser(another_user_id).getUserInfo());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("is_login_duplicated", false);
        SingletonLogger.getInstance().info("new user");
        return "users/new";
    }

    @PostMapping("/create_new_user")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        try {
            userService.createUser(user);
            SingletonLogger.getInstance().info("new user");
            return "redirect:/enter";
        } catch (DuplicateLoginException exception) {
            model.addAttribute("user", user);
            model.addAttribute("is_login_duplicated", true);
            SingletonLogger.getInstance().info("duplication error!");
            return "users/new";
        }
    }

    @GetMapping("/{user_id}/search/init_page")
    public String search(@PathVariable("user_id") int user_id,
                         Model model) {
        model.addAttribute("user_id", user_id);
        model.addAttribute("searching_user", new User());
        SingletonLogger.getInstance().info("user_id: " + String.valueOf(user_id));
        return "users/search";
    }

    @GetMapping("/{user_id}/search")
    public String search(@PathVariable("user_id") int user_id,
                         @ModelAttribute("searching_user") User searchingUser,
                         Model model) {
        model.addAttribute("user_id", user_id);
        model.addAttribute("searching_user", new User());
        model.addAttribute("found_users", userService.getAllSimilarUsers(searchingUser.getLogin()));
        SingletonLogger.getInstance().info("user_id: " + String.valueOf(user_id) + " searching user: " + String.valueOf(searchingUser.getId()));
        return "users/search";
    }
}
