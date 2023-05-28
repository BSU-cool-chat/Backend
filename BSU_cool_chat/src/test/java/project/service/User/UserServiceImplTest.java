package project.service.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import project.Exceptions.DuplicateLoginException;
import project.Exceptions.UserNotFoundException;
import project.dao.Chat.ChatDAOImpl;
import project.dao.User.UserDAOImpl;
import project.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


import static org.junit.jupiter.params.provider.Arguments.arguments;

class UserServiceImplTest {

    final String password_for_sudo = "SUDO_PASSWORD";

    static Stream<Arguments> ListProvider() {
        return Stream.of(
                arguments(List.of(new User(1, "adamenko", "qwerty", "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer"),
                        new User(2, "zhdun", "123456", "zhdun", "male", 20, "schoolboy"),
                        new User(3, "adamada", "rftg", "adamada", "male", 3, "baby"))),
                arguments(List.of()),
                arguments(List.of(new User(0, "adamenko", "qwerty", "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer")))
        );
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getAllUsers(List<User> users) throws IOException, DuplicateLoginException {
        UserServiceImpl userService = setUp();
        for (var user: users) {
            userService.createUser(user);
        }
        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users, users);

        // TODO DELETE USERS
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void createUser(List<User> users) throws IOException, DuplicateLoginException {
        UserServiceImpl userService = setUp();

        for (var user: users) {
              userService.createUser(user);
        }

        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users, users);

        // TODO DELETE USERS
    }

    @Test
    void deleteUser() throws IOException {
        // TODO
    }

    @Test
    void updateUser() throws IOException {
        // TODO
    }


    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getUser(List<User> users) throws IOException, UserNotFoundException, DuplicateLoginException {
        UserServiceImpl userService = setUp();
        for (var user: users) {
            userService.createUser(user);
        }
        for (int i = 0; i < users.size(); ++i) {
            Assertions.assertEquals(users.get(i), userService.getUser(i + 1));
        }
        // TODO DELETE
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getUserId(List<User> users) throws IOException, DuplicateLoginException {
        UserServiceImpl userService = setUp();
        for (var user: users) {
            userService.createUser(user);
        }
        for (int i = 0; i < users.size(); ++i) {
            Assertions.assertEquals(i + 1, userService.getUserId(users.get(i).getLogin(), users.get(i).getPassword()).get());
        }
        // TODO DELETE
    }

    UserServiceImpl setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/bsu_cool_chat_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UserDAOImpl userDAO = new UserDAOImpl(jdbcTemplate);
        ChatDAOImpl chatDAO = new ChatDAOImpl(jdbcTemplate);

        return new UserServiceImpl(userDAO, chatDAO);
    }
}