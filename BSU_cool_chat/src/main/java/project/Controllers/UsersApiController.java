package project.Controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.Exceptions.DuplicateLoginException;
import project.Exceptions.UserNotFoundException;
import project.service.User.UserService;
import project.models.User;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UsersApiController {
    private final UserService userService;

    @Autowired
    public UsersApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public String listUsers() {
        JsonObject jsonObject = new JsonObject();
        JsonArray users = new JsonArray();
        for (User user : userService.getAllUsers()) {
            JsonObject user_info = new JsonObject();
            user_info.addProperty("login", user.getLogin());
            user_info.addProperty("password", user.getPassword());
            users.add(user_info);
        }
        jsonObject.add("users", users);
        return jsonObject.toString();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String user(@PathVariable("id") int id) {
        try {
            User user = userService.getUser(id);
            JsonObject user_info = new JsonObject();
            user_info.addProperty("login", user.getLogin());
            user_info.addProperty("password", user.getPassword());
            return user_info.toString();
        }catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
//        TODO not tested
        try {
            userService.createUser(user);
        } catch (DuplicateLoginException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") int id) {
//        TODO not tested
        userService.deleteUser(id);
    }
}
