package project.dao.Chat;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import project.config.databases.DatabaseInitializerImplementation;
import project.dao.Message.MessageDAOImpl;
import project.dao.User.UserDAOImpl;
import project.service.Chat.ChatServiceImpl;
import project.service.Message.MessageServiceImpl;
import project.service.User.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class ChatDAOImplTest {

    final String password_for_sudo = "123852951";

    @Test
    void getAllUsersChats() {
    }

    @Test
    void getChat() {
    }

    @Test
    void getOrCreateStandardChat() {
    }

    @Test
    void getAllChatMembers() {
    }

    void runCommand(String file) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(file, password_for_sudo);
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
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