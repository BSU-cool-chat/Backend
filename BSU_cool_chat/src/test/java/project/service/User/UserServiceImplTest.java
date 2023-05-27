package project.service.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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


import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    final String password_for_sudo = "SUDO_PASSWORD";

    @Test
    void getAllUsers() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");

        UserServiceImpl userService = setUp();
        var all_users = userService.getAllUsers();

        Assertions.assertEquals(all_users.size(), 3);

        Assertions.assertEquals(all_users.get(0).getId(), 0);
        Assertions.assertEquals(all_users.get(1).getId(), 1);
        Assertions.assertEquals(all_users.get(2).getId(), 2);

        Assertions.assertEquals(all_users.get(0).getLogin(), "adamenko");
        Assertions.assertEquals(all_users.get(1).getLogin(), "zhdun");
        Assertions.assertEquals(all_users.get(2).getLogin(), "adamada");

        Assertions.assertEquals(all_users.get(0).getPassword(), "qwerty");
        Assertions.assertEquals(all_users.get(1).getPassword(), "123456");
        Assertions.assertEquals(all_users.get(2).getPassword(), "rftgyhyuj");

        Assertions.assertEquals(all_users.get(0).getUserInfo().getId(), 0);
        Assertions.assertEquals(all_users.get(1).getUserInfo().getId(), 1);
        Assertions.assertEquals(all_users.get(2).getUserInfo().getId(), 2);

        Assertions.assertEquals(all_users.get(0).getUserInfo().getLogin(), "adamenko");
        Assertions.assertEquals(all_users.get(1).getUserInfo().getLogin(), "zhdun");
        Assertions.assertEquals(all_users.get(2).getUserInfo().getLogin(), "adamada");

        Assertions.assertEquals(all_users.get(0).getUserInfo().getAllInfo(), "Name\tadamenko\n" +
                "Sex\tmale\n" +
                "Age\t19\n" +
                "AAdditionalInfo\tYSDA 1st year student, BSU 2nd year student, swimmer\n");

        Assertions.assertEquals(all_users.get(1).getUserInfo().getAllInfo(), "Name\tzhdun\n" +
                "Sex\tmale\n" +
                "Age\t15\n" +
                "AAdditionalInfo\tschoolboy\n");

        Assertions.assertEquals(all_users.get(2).getUserInfo().getAllInfo(), "Name\tadamada\n" +
                "Sex\tmale\n" +
                "Age\t3\n" +
                "AAdditionalInfo\tbaby\n");

        runCommand("src/test/clear_database.sh");
    }

    @Test
    void createUser() throws IOException, DuplicateLoginException {
        runCommand("src/test/empty_database.sh");

        UserServiceImpl userService = setUp();
        userService.createUser(new User(0, "adamenko", "qwerty", "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer"));

        var all_users = userService.getAllUsers();

        Assertions.assertEquals(all_users.size(), 1);

        Assertions.assertEquals(all_users.get(0).getId(), 1);
        Assertions.assertEquals(all_users.get(0).getLogin(), "adamenko");
        Assertions.assertEquals(all_users.get(0).getPassword(), "qwerty");
        Assertions.assertEquals(all_users.get(0).getUserInfo().getId(), 1);
        Assertions.assertEquals(all_users.get(0).getUserInfo().getLogin(), "adamenko");
        Assertions.assertNotEquals(all_users.get(0).getUserInfo().getAllInfo(), "Name\tadamenko\n" +
                "Sex\tmale\n" +
                "Age\t19\n" +
                "AAdditionalInfo\tYSDA 1st year student, BSU 2nd year student, swimmer\n");

        runCommand("src/test/clear_database.sh");
    }

    @Test
    void deleteUser() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");

        UserServiceImpl userService = setUp();

        Assertions.assertThrows(RuntimeException.class, () -> userService.deleteUser(3));

        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users.size(), 3);

        Assertions.assertEquals(all_users.get(0).getId(), 0);
        Assertions.assertEquals(all_users.get(1).getId(), 1);
        Assertions.assertEquals(all_users.get(2).getId(), 2);

        Assertions.assertEquals(all_users.get(0).getLogin(), "adamenko");
        Assertions.assertEquals(all_users.get(1).getLogin(), "zhdun");
        Assertions.assertEquals(all_users.get(2).getLogin(), "adamada");

        Assertions.assertEquals(all_users.get(0).getPassword(), "qwerty");
        Assertions.assertEquals(all_users.get(1).getPassword(), "123456");
        Assertions.assertEquals(all_users.get(2).getPassword(), "rftgyhyuj");

        Assertions.assertEquals(all_users.get(0).getUserInfo().getId(), 0);
        Assertions.assertEquals(all_users.get(1).getUserInfo().getId(), 1);
        Assertions.assertEquals(all_users.get(2).getUserInfo().getId(), 2);

        Assertions.assertEquals(all_users.get(0).getUserInfo().getLogin(), "adamenko");
        Assertions.assertEquals(all_users.get(1).getUserInfo().getLogin(), "zhdun");
        Assertions.assertEquals(all_users.get(2).getUserInfo().getLogin(), "adamada");

        Assertions.assertEquals(all_users.get(0).getUserInfo().getAllInfo(), "Name\tadamenko\n" +
                "Sex\tmale\n" +
                "Age\t19\n" +
                "AAdditionalInfo\tYSDA 1st year student, BSU 2nd year student, swimmer\n");

        Assertions.assertEquals(all_users.get(1).getUserInfo().getAllInfo(), "Name\tzhdun\n" +
                "Sex\tmale\n" +
                "Age\t15\n" +
                "AAdditionalInfo\tschoolboy\n");

        Assertions.assertEquals(all_users.get(2).getUserInfo().getAllInfo(), "Name\tadamada\n" +
                "Sex\tmale\n" +
                "Age\t3\n" +
                "AAdditionalInfo\tbaby\n");

        runCommand("src/test/clear_database.sh");
    }

    @Test
    void updateUser() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");

        UserServiceImpl userService = setUp();

        Assertions.assertThrows(RuntimeException.class, () -> userService.updateUser(new User(1, "adamenko", "qwerty", "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer")));

        var all_users = userService.getAllUsers();
        Assertions.assertEquals(all_users.size(), 3);

        Assertions.assertEquals(all_users.get(0).getId(), 0);
        Assertions.assertEquals(all_users.get(1).getId(), 1);
        Assertions.assertEquals(all_users.get(2).getId(), 2);

        Assertions.assertEquals(all_users.get(0).getLogin(), "adamenko");
        Assertions.assertEquals(all_users.get(1).getLogin(), "zhdun");
        Assertions.assertEquals(all_users.get(2).getLogin(), "adamada");

        Assertions.assertEquals(all_users.get(0).getPassword(), "qwerty");
        Assertions.assertEquals(all_users.get(1).getPassword(), "123456");
        Assertions.assertEquals(all_users.get(2).getPassword(), "rftgyhyuj");

        Assertions.assertEquals(all_users.get(0).getUserInfo().getId(), 0);
        Assertions.assertEquals(all_users.get(1).getUserInfo().getId(), 1);
        Assertions.assertEquals(all_users.get(2).getUserInfo().getId(), 2);

        Assertions.assertEquals(all_users.get(0).getUserInfo().getLogin(), "adamenko");
        Assertions.assertEquals(all_users.get(1).getUserInfo().getLogin(), "zhdun");
        Assertions.assertEquals(all_users.get(2).getUserInfo().getLogin(), "adamada");

        Assertions.assertEquals(all_users.get(0).getUserInfo().getAllInfo(), "Name\tadamenko\n" +
                "Sex\tmale\n" +
                "Age\t19\n" +
                "AAdditionalInfo\tYSDA 1st year student, BSU 2nd year student, swimmer\n");

        Assertions.assertEquals(all_users.get(1).getUserInfo().getAllInfo(), "Name\tzhdun\n" +
                "Sex\tmale\n" +
                "Age\t15\n" +
                "AAdditionalInfo\tschoolboy\n");

        Assertions.assertEquals(all_users.get(2).getUserInfo().getAllInfo(), "Name\tadamada\n" +
                "Sex\tmale\n" +
                "Age\t3\n" +
                "AAdditionalInfo\tbaby\n");

        runCommand("src/test/clear_database.sh");
    }


    @Test
    void getUser() throws IOException, UserNotFoundException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");

        UserServiceImpl userService = setUp();

        var all_users = userService.getAllUsers();

        Assertions.assertEquals(all_users.size(), 3);

        User user;
        try {
            user = userService.getUser(0);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(user.getId(), 0);
        Assertions.assertEquals(user.getLogin(), "adamenko");
        Assertions.assertEquals(user.getPassword(), "qwerty");
        Assertions.assertEquals(user.getUserInfo().getId(), 0);
        Assertions.assertEquals(user.getUserInfo().getLogin(), "adamenko");
        Assertions.assertEquals(user.getUserInfo().getAllInfo(), "Name\tadamenko\n" +
                "Sex\tmale\n" +
                "Age\t19\n" +
                "AAdditionalInfo\tYSDA 1st year student, BSU 2nd year student, swimmer\n");

        runCommand("src/test/clear_database.sh");
    }

    @Test
    void getUserId() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");

        UserServiceImpl userService = setUp();

        var all_users = userService.getAllUsers();

        Assertions.assertEquals(all_users.size(), 3);


        Assertions.assertEquals(userService.getUserId("adamenko", "qwerty").get(), 0);
        Assertions.assertEquals(userService.getUserId("zhdun", "123456").get(), 1);
        Assertions.assertEquals(userService.getUserId("adamada", "rftgyhyuj").get(), 2);

        runCommand("src/test/clear_database.sh");
    }

    void runCommand(String file) throws IOException {
//        ProcessBuilder pb = new ProcessBuilder(file, password_for_sudo);
//        Process p = pb.start();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//        String line = null;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
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