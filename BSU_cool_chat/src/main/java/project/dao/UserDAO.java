package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.models.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
//        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public User getUser(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new Object[]{id}, new UserMapper())
                .stream()
                .findAny()
                .orElse(null);
//                .orElse(new Error(id + "not found"));
    }

    public void addUser(User user) {
        if (jdbcTemplate.query("SELECT * FROM users WHERE login=?",
                new Object[]{user.getLogin()},
                new UserMapper()).stream().findAny().isPresent()) {
            throw new RuntimeException("user with login \"" + user.getLogin() + "\" already exists");
        }
        jdbcTemplate.update("INSERT INTO users(login, password) VALUES (?, ?);",user.getLogin(), user.getPassword());
    }

    public void updateUser(User user) {
        jdbcTemplate.update("UPDATE users SET login=?, password=? WHERE id=?",
                user.getLogin(),
                user.getPassword(),
                user.getId());
    }

    public Optional<Integer> getUserId(String login, String password) {
        var searching_user = getUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findAny();
        return searching_user.map(user -> Math.toIntExact(user.getId()));
    }
}
