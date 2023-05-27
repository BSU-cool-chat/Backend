package project.service.Chat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import project.config.databases.DatabaseInitializerImplementation;
import project.dao.Chat.ChatDAOImpl;
import project.dao.Message.MessageDAOImpl;
import project.dao.User.UserDAOImpl;
import project.service.Message.MessageServiceImpl;
import project.service.User.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class ChatServiceImplTest {

    final String password_for_sudo = "SUDO_PASSWORD";

    @Test
    void getAllUsersChats() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");
        runCommand("src/test/add_messages.sh");

        ChatServiceImpl chatService = setUp();

        var all_chats = chatService.getAllUsersChats(0);

        Assertions.assertEquals(all_chats.size(), 2);

        Assertions.assertEquals(all_chats.get(0).getId(), 0);
        Assertions.assertEquals(all_chats.get(1).getId(), 1);

        Assertions.assertEquals(all_chats.get(0).getMembers().size(), 2);
        Assertions.assertEquals(all_chats.get(1).getMembers().size(), 2);

        Assertions.assertEquals(all_chats.get(0).getMembers().get(0).getLogin(), "adamenko");
        Assertions.assertEquals(all_chats.get(0).getMembers().get(0).getId(), 0);


        runCommand("src/test/clear_database.sh");
    }

    @Test
    void getAllUsersChatsInfo() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");
        runCommand("src/test/add_messages.sh");

        ChatServiceImpl chatService = setUp();

        var all_info = chatService.getAllUsersChatsInfo(0);

        Assertions.assertEquals(all_info.size(), 2);

        Assertions.assertEquals(all_info.get(0).getLastMessage().getChatId(), 0);
        Assertions.assertEquals(all_info.get(1).getLastMessage().getChatId(), 1);

        runCommand("src/test/clear_database.sh");
    }

    @Test
    void getChat() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");
        runCommand("src/test/add_messages.sh");

        ChatServiceImpl chatService = setUp();

        var chat = chatService.getChat(0);

        Assertions.assertEquals(chat.getId(), 0);
        Assertions.assertEquals(chat.getMembers().get(0).getId(), 0);
        Assertions.assertEquals(chat.getMembers().get(1).getId(), 1);

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
