package project.service.Chat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import project.config.databases.DatabaseInitializerImplementation;
import project.dao.Chat.ChatDAOImpl;
import project.dao.Message.MessageDAOImpl;
import project.dao.User.UserDAOImpl;
import project.models.User;
import project.service.Message.MessageServiceImpl;
import project.service.User.UserServiceImpl;

import java.io.IOException;
import java.util.List;

class ChatServiceImplTest {

    @Test
    @DisplayName("Test")
    @Parameterized.Parameters()
    void getAllUsersChats() throws IOException {
        ChatServiceImpl chatService = setUp();
        var all_chats = chatService.getAllUserChats(0);

    }

    @Test
    void getAllUsersChatsInfo() throws IOException {
        ChatServiceImpl chatService = setUp();
        var all_info = chatService.getAllUsersChatsInfo(0);
    }

    @Test
    void getChat() throws IOException {
        ChatServiceImpl chatService = setUp();
        var chat = chatService.getChat(0);

    }

    ChatServiceImpl setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/bsu_cool_chat_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        ChatDAOImpl chatDAO = new ChatDAOImpl(jdbcTemplate);
        MessageDAOImpl messageDAO = new MessageDAOImpl(new DatabaseInitializerImplementation(jdbcTemplate), jdbcTemplate);
        UserDAOImpl userDAO = new UserDAOImpl(jdbcTemplate);

        return new ChatServiceImpl(chatDAO, new MessageServiceImpl(messageDAO, userDAO), new UserServiceImpl(userDAO, chatDAO));
    }
}