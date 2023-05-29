package project.service.Message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import project.config.databases.DatabaseInitializerImplementation;
import project.dao.Chat.ChatDAOImpl;
import project.dao.Message.MessageDAO;
import project.dao.Message.MessageDAOImpl;
import project.dao.User.UserDAOImpl;
import project.models.Message;
import project.models.User;
import project.service.User.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MessageServiceImplTest {

    static Stream<Arguments> ListProvider() {
        // TODO
        return Stream.of(
                arguments(List.of(new User(1, "adamenko", "qwerty", false, "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer"),
                                new User(2, "zhdun", "123456", false, "zhdun", "male", 20, "schoolboy"),
                                new User(3, "adamada", "rftg", false, "adamada", "male", 3, "baby")),
                        List.of(new Message(), new Message())),
                arguments(List.of(), List.of()),
                arguments(List.of(new User(0, "adamenko", "qwerty", false, "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer")),
                        List.of(new Message(), new Message()))
        );
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getAllMessages(List<User> users, List<Message> messages) throws IOException {
        MessageServiceImpl messageService = setUp();

        for (User user: users) {
            // TODO add users
        }

        for (Message message : messages) {
            messageService.createMessage(message);
        }

        var all_messages = messageService.getAllMessages(0);
        Assertions.assertEquals(all_messages, messages);
    }

    @ParameterizedTest(name="#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void createMessage(List<User> users, List<Message> messages) throws IOException {
        MessageServiceImpl messageService = setUp();

        for (User user: users) {
            // TODO add users
        }

        for (Message message : messages) {
            messageService.createMessage(message);
        }

        var all_messages = messageService.getAllMessages(0);
        Assertions.assertEquals(all_messages, messages);
    }

    MessageServiceImpl setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/bsu_cool_chat_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MessageDAOImpl messageDAO = new MessageDAOImpl(new DatabaseInitializerImplementation(jdbcTemplate), jdbcTemplate);
        UserDAOImpl userDAO = new UserDAOImpl(jdbcTemplate);

        return new MessageServiceImpl(messageDAO, userDAO);
    }
}