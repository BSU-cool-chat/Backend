package project.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.Exceptions.DuplicateLoginException;
import project.dao.User.UserService;
import project.models.User;

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
        model.addAttribute("supervising_user", userService.getUser(another_user_id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("/create_new_user")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        try {
            userService.createUser(user);
            return "redirect:/enter";
        } catch (DuplicateLoginException exception) {
            System.out.println(exception);
            return "redirect:/users/create_new_user";
        }
    }

    @GetMapping("/{user_id}/search/init_page")
    public String search(@PathVariable("user_id") int user_id,
                         Model model) {
        model.addAttribute("user_id", user_id);
        model.addAttribute("searching_user", new User());
        return "users/search";
    }

    @GetMapping("/{user_id}/search")
    public String search(@PathVariable("user_id") int user_id,
                         @ModelAttribute("searching_user") User searchingUser,
                         Model model) {
        model.addAttribute("user_id", user_id);
        model.addAttribute("searching_user", new User());
        model.addAttribute("found_users", userService.getAllSimilarUsers(searchingUser.getLogin()));
        return "users/search";
    }
}
