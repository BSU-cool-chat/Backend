package project.service.Chat;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;
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
import project.service.Message.MessageServiceImpl;
import project.service.User.UserServiceImpl;
import project.utils.Pair;
import project.utils.Services;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class ChatServiceImplTest {

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
    void getAllUserChats(List<User> users, List<Message> messages, List<Pair<Integer, Integer>> chats) throws DuplicateLoginException {
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

        for (var user : users) {
            List<Chat> all_chats = services.chatService.getAllUserChats(user.getId());

            List<Chat> expected_chats = new ArrayList<>();
            for (int i = 1; i <= chats.size(); ++i) {
                if (chats.get(i - 1).getLeft() == user.getId() || chats.get(i - 1).getRight() == user.getId()) {
                    String name = "Chat between " + users.get(chats.get(i - 1).getLeft() - 1).getLogin() + " and " + users.get(chats.get(i - 1).getRight() - 1).getLogin();
                    expected_chats.add(new Chat(i, name, false));
                }
            }
            Assertions.assertEquals(all_chats, expected_chats);
        }
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("ListProvider")
    void getChat(List<User> users, List<Message> messages, List<Pair<Integer, Integer>> chats) throws DuplicateLoginException {
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

        for (int i = 1; i <= chats.size(); ++i) {
            String name = "Chat between " + users.get(chats.get(i - 1).getLeft() - 1).getLogin() + " and " + users.get(chats.get(i - 1).getRight() - 1).getLogin();
            var chat = new Chat(i, name, false);
            Assertions.assertEquals(chat, services.chatService.getChat(i));
        }
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
