package project.service.Message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import project.Exceptions.DuplicateLoginException;
import project.config.databases.DatabaseInitializerImplementation;
import project.dao.Chat.ChatDAOImpl;
import project.dao.Message.MessageDAOImpl;
import project.dao.User.UserDAOImpl;
import project.models.Chat;
import project.models.Message;
import project.models.User;
import project.service.Chat.ChatServiceImpl;
import project.service.User.UserServiceImpl;
import project.utils.Pair;
import project.utils.Services;

import java.util.*;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalTime.now;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MessageServiceImplTest {

    static Stream<Arguments> ListProvider() {
        return Stream.of(
                arguments(List.of(new User(1, "adamenko", "qwerty", false, "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer"),
                                new User(2, "zhdun", "123456", false, "zhdun", "male", 20, "schoolboy"),
                                new User(3, "adamada", "rftg", false, "adamada", "male", 3, "baby")),
                        List.of(new Message(1,
                                        new User(1, "adamenko", "qwerty", false, "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer"),
                                        1,
                                        "How are you?",
                                        new Date(),
                                        new Time(1685134281)),
                                new Message(2,
                                        new User(2, "zhdun", "123456", false, "zhdun", "male", 20, "schoolboy"),
                                        1,
                                        "I am ok!",
                                        new Date(),
                                        new Time(1685134290)),
                                new Message(3,
                                        new User(3, "adamada", "rftg", false, "adamada", "male", 3, "baby"),
                                        2,
                                        "Hi!",
                                        new Date(),
                                        new Time(1685134500))),
                        List.of(new Pair<Integer, Integer>(1, 2),
                                new Pair<Integer, Integer>(1, 3),
                                new Pair<Integer, Integer>(2, 3))),
                arguments(List.of(), List.of(), List.of())
        );
    }


    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getAllMessagesAndCreateChat(List<User> users, List<Message> messages, List<Pair<Integer, Integer>> chats) throws IOException, DuplicateLoginException {
        Services services = setUp();

        for (User user : users) {
            services.userService.createUser(user);
        }

        for (var chat : chats) {
            services.chatService.getOrCreateStandardChat(chat.getLeft(), chat.getRight());
        }

        for (Message message : messages) {
            services.messageService.createMessage(message);
        }

        List<Message> all_messages = new ArrayList<>();
        for (int i = 1; i <= chats.size(); ++i) {
            all_messages.addAll(services.messageService.getAllMessages(i));
        }
        Assertions.assertEquals(all_messages, messages);
    }

    Services setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/bsu_cool_chat_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MessageDAOImpl messageDAO = new MessageDAOImpl(new DatabaseInitializerImplementation(jdbcTemplate), jdbcTemplate);
        UserDAOImpl userDAO = new UserDAOImpl(jdbcTemplate);
        ChatDAOImpl chatDAO = new ChatDAOImpl(jdbcTemplate);

        var messageService = new MessageServiceImpl(messageDAO, userDAO);
        var userService = new UserServiceImpl(userDAO, chatDAO);
        var chatService = new ChatServiceImpl(chatDAO, messageService, userService);
        return new Services(messageService, userService, chatService);
    }
}

