package project.dao;

import org.springframework.stereotype.Component;
import project.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private static long user_count;
    private List<User> users;

    {
        users = new ArrayList<User>();
        users.add(new User(user_count++, "Admin", "1111"));
        users.add(new User(user_count++, "LOL", "parolol"));
        users.add(new User(user_count++, "KEK", "parolkek"));
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(long id) {
        return users.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    public void addUser(User user) {
        user.setId(user_count++);
        users.add(user);
    }

    public Optional<Integer> getUserId(String login, String password) {
        var searching_user = getUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findAny();
        return searching_user.map(user -> Math.toIntExact(user.getId()));
    }
}
