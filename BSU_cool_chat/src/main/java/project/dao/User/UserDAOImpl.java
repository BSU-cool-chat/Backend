package project.dao.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.Exceptions.DuplicateLoginException;
import project.Exceptions.UserNotFoundException;
import project.dao.IdMapper;
import project.models.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query("""
                SELECT users.id, login, password, name, sex, age, additional_info
                FROM users
                    INNER JOIN users_info on users.id = users_info.user_id;
                """, new UserMapper());
    }

    public void createUser(User user) throws DuplicateLoginException {
        if (getAllUsers().stream().anyMatch(existing_user -> existing_user.getLogin().equals(user.getLogin()))) {
            throw new DuplicateLoginException("User with login \"" + user.getLogin() + "\" already exists");
        }
        boolean need = true;
        int user_id = 0;
        while (need) {
            need = false;
            try {
                user_id = jdbcTemplate.query("""
                                INSERT INTO users(login, password)
                                VALUES (?, ?)
                                RETURNING id""",
                                new IdMapper(),
                                user.getLogin(), user.getPassword()).stream()
                        .findAny().get();
//              TODO maybe should add name, sex, age, additional info
            } catch (Exception e) {
                need = true;
            }
        }
        jdbcTemplate.update(" INSERT INTO users_info(user_id) VALUES(?) ", user_id);
    }

    public void deleteUser(int id) {
//        TODO
        throw new RuntimeException("no such method implementation");
    }

    public void updateUser(User user) {
        throw new RuntimeException("no such method implementation");
    }

    public User getUser(int id) throws UserNotFoundException {
        return getAllUsers().stream()
                .filter(user -> user.getId() == id)
                .findAny()
                .orElseThrow(() -> new UserNotFoundException(id + "not found"));
    }

    public Optional<Integer> getUserId(String login, String password) {
        var searching_user = getAllUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findAny();
        return searching_user.map(user -> Math.toIntExact(user.getId()));
    }


    @Override
    public List<User> getAllSimilarUsers(String searching_login) {
        return getAllUsers().stream()
                .filter(user -> user.getLogin().toLowerCase(Locale.ROOT).contains(searching_login.toLowerCase()))
                .toList();
    }
}
