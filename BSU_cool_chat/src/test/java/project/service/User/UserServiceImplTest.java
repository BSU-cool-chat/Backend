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

    static Stream<Arguments> ListProvider() {
        return Stream.of(
                arguments(List.of(new User(1, "adamenko", "qwerty", false, "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer"),
                        new User(2, "zhdun", "123456", false, "zhdun", "male", 20, "schoolboy"),
                        new User(3, "adamada", "rftg", false, "adamada", "male", 3, "baby"))),
                arguments(List.of()),
                arguments(List.of(new User(4, "adamenko", "qwerty", false, "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer")))
        );
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getAllUsers(List<User> users) throws DuplicateLoginException {
        UserServiceImpl userService = setUp();
        for (var user: users) {
            userService.createUser(user);
        }

        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users, users);

        for (var user: all_users) {
            userService.deleteUser(user.getId());
        }
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void createUser(List<User> users) throws IOException, DuplicateLoginException {
        UserServiceImpl userService = setUp();

        for (var user: users) {
            Assertions.assertDoesNotThrow(()->userService.createUser(user));
        }

        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users, users);

        for (var user: all_users) {
            userService.deleteUser(user.getId());
        }
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void deleteUser(List<User> users) throws IOException, DuplicateLoginException {
        UserServiceImpl userService = setUp();

        for (var user: users) {
            userService.createUser(user);
        }

        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users, users);

        all_users = userService.getAllUsers();

        for (var user: all_users) {
            Assertions.assertDoesNotThrow(() -> userService.deleteUser(user.getId()));
        }

        Assertions.assertTrue(userService.getAllUsers().isEmpty());
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void updateUser(List<User> users) throws DuplicateLoginException {
        UserServiceImpl userService = setUp();
        for (var user: users) {
            userService.createUser(user);
        }

        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users, users);

        for (User value : users) {
            value.setName("ADAMADA");
            userService.updateUser(value);
        }

        all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users, users);

        for (var user: all_users) {
            userService.deleteUser(user.getId());
        }
    }


    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getUser(List<User> users) throws UserNotFoundException, DuplicateLoginException {
        UserServiceImpl userService = setUp();
        for (var user: users) {
            userService.createUser(user);
        }

        var all_users = userService.getAllUsers();

        for (int i = 0; i < users.size(); ++i) {
            Assertions.assertEquals(all_users.get(i), userService.getUser(all_users.get(i).getId()));
        }

        for (var user: all_users) {
            userService.deleteUser(user.getId());
        }
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getUserId(List<User> users) throws DuplicateLoginException {
        UserServiceImpl userService = setUp();
        for (var user: users) {
            userService.createUser(user);
        }
        var all_users = userService.getAllUsers();
        for (int i = 0; i < users.size(); ++i) {
            Assertions.assertEquals(all_users.get(i).getId(), userService.getUserId(users.get(i).getLogin(), users.get(i).getPassword()).get());
        }
        for (var user: all_users) {
            userService.deleteUser(user.getId());
        }
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