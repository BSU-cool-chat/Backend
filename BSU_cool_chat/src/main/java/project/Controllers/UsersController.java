package project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.dao.User.UserService;
import project.models.User;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/{id}/supervise/{another_user_id}")
    public String show(@PathVariable("id") int user_id,
                       @PathVariable("another_user_id") int another_user_id,
                       Model model) {
        model.addAttribute("user_id", user_id);
        model.addAttribute("supervising_user", userService.getUser(another_user_id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/search/{user_id}/init_page")
    public String search(@PathVariable("user_id") int user_id,
                         Model model) {
        model.addAttribute("user_id", user_id);
        model.addAttribute("searching_user", new User());
        return "users/search";
    }

    @GetMapping("/search/{user_id}")
    public String search(@PathVariable("user_id") int user_id,
                         @ModelAttribute("searching_user") User searchingUser,
                         Model model) {
        model.addAttribute("user_id", user_id);
        model.addAttribute("searching_user", new User());
        model.addAttribute("found_users", userService.getAllSimilarUsers(searchingUser.getLogin()));
        return "users/search";
    }
}
