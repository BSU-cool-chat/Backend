package project.dao.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.Exceptions.DuplicateLoginException;
import project.config.databases.DatabaseInitializer;
import project.dao.Chat.IdMapper;
import project.models.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO implements UserService {
    private final DatabaseInitializer databaseInitializer;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(DatabaseInitializer databaseInitializer, JdbcTemplate jdbcTemplate) {
        this.databaseInitializer = databaseInitializer;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void postConstruct() {
        databaseInitializer.initDatabases();
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query("""
                SELECT users.id, login, password, name, sex, age, additional_info
                FROM users
                    INNER JOIN users_info on users.id = users_info.user_id;
                """, new UserMapper());
//        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public void createUser(User user) throws DuplicateLoginException {
        if (jdbcTemplate.query("SELECT * FROM users WHERE login=?;",
                new Object[]{user.getLogin()},
                new IdMapper()).stream().findAny().isPresent()) {
            throw new DuplicateLoginException("User with login \"" + user.getLogin() + "\" already exists");
        }
        int user_id = jdbcTemplate.query("""
                                INSERT INTO users(login, password)
                                VALUES (?, ?)
                                RETURNING id""",
                        new IdMapper(),
                        user.getLogin(), user.getPassword()).stream()
                .findAny().get();
//        TODO maybe should add name, sex, age, additional info
        jdbcTemplate.update(" INSERT INTO users_info(user_id) VALUES(?) ", user_id);
    }

    public void deleteUser(int id) {
        throw new RuntimeException("no such method implementation");
    }

    public void updateUser(User user) {
        throw new RuntimeException("no such method implementation");
    }

    public User getUser(int id) {
        return jdbcTemplate.query("""
                        SELECT users.id, login, password, name, sex, age, additional_info
                        FROM users
                            INNER JOIN users_info ON users.id = users_info.user_id
                        WHERE users.id=?
                        """, new Object[]{id}, new UserMapper())
                .stream()
                .findAny()
                .orElse(null);
//                .orElse(new Error(id + "not found"));
    }

    public Optional<Integer> getUserId(String login, String password) {
        var searching_user = getAllUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findAny();
        return searching_user.map(user -> Math.toIntExact(user.getId()));
    }

    @Override
    public List<User> getAllChatMembers(int chat_id) {
        var members = jdbcTemplate.query(
                """
                        SELECT users.id, login, password, name, sex, age, additional_info
                        FROM chats_members
                                INNER JOIN users ON member_id = users.id
                                INNER JOIN users_info ON users.id = users_info.user_id
                        WHERE chat_id = ?;""",
                new UserMapper(),
                chat_id).stream().toList();
        return members;
    }

    @Override
    public List<User> getAllSimilarUsers(String searching_login) {
        var found_users = jdbcTemplate.query(
                """
                        SELECT users.id, login, password, name, sex, age, additional_info
                        FROM users
                            INNER JOIN users_info on users.id = users_info.user_id
                        WHERE LOWER(login) LIKE '%' || (LOWER(?)) || '%';""",
                new UserMapper(),
                searching_login).stream().toList();
        return found_users;
    }
}
