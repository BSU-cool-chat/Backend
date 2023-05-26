package project.service.Message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceImplTest {

    final String password_for_sudo = "123852951";

    @Test
    void getAllMessages() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");
        runCommand("src/test/add_messages.sh");

        MessageServiceImpl messageService = setUp();
        var all_messages = messageService.getAllMessages(0);
        Assertions.assertEquals(all_messages.size(), 4);

        Assertions.assertEquals(all_messages.get(0).getId(), 0);
        Assertions.assertEquals(all_messages.get(1).getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getId(), 2);
        Assertions.assertEquals(all_messages.get(3).getId(), 3);

        Assertions.assertEquals(all_messages.get(0).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(1).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(2).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(3).getChatId(), 0);

        Assertions.assertEquals(all_messages.get(0).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(1).getSender().getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(3).getSender().getId(), 1);

        Assertions.assertEquals(all_messages.get(0).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(1).getSender().getLogin(), "zhdun");
        Assertions.assertEquals(all_messages.get(2).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(3).getSender().getLogin(), "zhdun");

        Assertions.assertEquals(all_messages.get(0).getText(), "Hi! How are you?");
        Assertions.assertEquals(all_messages.get(1).getText(), "I am OK. And you?");
        Assertions.assertEquals(all_messages.get(2).getText(), "I am fine. What are doing tonight?");
        Assertions.assertEquals(all_messages.get(3).getText(), "I am coding a project:)");


        // -----------------------------------------------------------

        all_messages = messageService.getAllMessages(1);

        Assertions.assertEquals(all_messages.get(0).getId(), 4);
        Assertions.assertEquals(all_messages.get(1).getId(), 5);
        Assertions.assertEquals(all_messages.get(2).getId(), 6);
        Assertions.assertEquals(all_messages.get(3).getId(), 7);

        Assertions.assertEquals(all_messages.get(0).getChatId(), 1);
        Assertions.assertEquals(all_messages.get(1).getChatId(), 1);
        Assertions.assertEquals(all_messages.get(2).getChatId(), 1);
        Assertions.assertEquals(all_messages.get(3).getChatId(), 1);

        Assertions.assertEquals(all_messages.get(0).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(1).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(2).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(3).getSender().getId(), 0);

        Assertions.assertEquals(all_messages.get(0).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(1).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(2).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(3).getSender().getLogin(), "adamenko");

        Assertions.assertEquals(all_messages.get(0).getText(), "1");
        Assertions.assertEquals(all_messages.get(1).getText(), "2");
        Assertions.assertEquals(all_messages.get(2).getText(), "3");
        Assertions.assertEquals(all_messages.get(3).getText(), "4");

        // -----------------------------------------------------------

        all_messages = messageService.getAllMessages(2);

        Assertions.assertEquals(all_messages.get(0).getId(), 8);
        Assertions.assertEquals(all_messages.get(1).getId(), 9);
        Assertions.assertEquals(all_messages.get(2).getId(), 10);
        Assertions.assertEquals(all_messages.get(3).getId(), 11);

        Assertions.assertEquals(all_messages.get(0).getChatId(), 2);
        Assertions.assertEquals(all_messages.get(1).getChatId(), 2);
        Assertions.assertEquals(all_messages.get(2).getChatId(), 2);
        Assertions.assertEquals(all_messages.get(3).getChatId(), 2);

        Assertions.assertEquals(all_messages.get(0).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(1).getSender().getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(3).getSender().getId(), 1);

        Assertions.assertEquals(all_messages.get(0).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(1).getSender().getLogin(), "zhdun");
        Assertions.assertEquals(all_messages.get(2).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(3).getSender().getLogin(), "zhdun");

        Assertions.assertEquals(all_messages.get(0).getText(), "Wa");
        Assertions.assertEquals(all_messages.get(1).getText(), "Ok");
        Assertions.assertEquals(all_messages.get(2).getText(), "Re");
        Assertions.assertEquals(all_messages.get(3).getText(), "Pe");

        runCommand("src/test/clear_database.sh");
    }

    @Test
    void createMessage() throws IOException {
        runCommand("src/test/empty_database.sh");
        runCommand("src/test/add_users.sh");
        runCommand("src/test/add_messages.sh");

        MessageServiceImpl messageService = setUp();
        var all_messages = messageService.getAllMessages(0);
        Assertions.assertEquals(all_messages.size(), 4);

        Assertions.assertEquals(all_messages.get(0).getId(), 0);
        Assertions.assertEquals(all_messages.get(1).getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getId(), 2);
        Assertions.assertEquals(all_messages.get(3).getId(), 3);

        Assertions.assertEquals(all_messages.get(0).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(1).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(2).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(3).getChatId(), 0);

        Assertions.assertEquals(all_messages.get(0).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(1).getSender().getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(3).getSender().getId(), 1);

        Assertions.assertEquals(all_messages.get(0).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(1).getSender().getLogin(), "zhdun");
        Assertions.assertEquals(all_messages.get(2).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(3).getSender().getLogin(), "zhdun");

        Assertions.assertEquals(all_messages.get(0).getText(), "Hi! How are you?");
        Assertions.assertEquals(all_messages.get(1).getText(), "I am OK. And you?");
        Assertions.assertEquals(all_messages.get(2).getText(), "I am fine. What are doing tonight?");
        Assertions.assertEquals(all_messages.get(3).getText(), "I am coding a project:)");


        // -----------------------------------------------------------

        all_messages = messageService.getAllMessages(1);

        Assertions.assertEquals(all_messages.get(0).getId(), 4);
        Assertions.assertEquals(all_messages.get(1).getId(), 5);
        Assertions.assertEquals(all_messages.get(2).getId(), 6);
        Assertions.assertEquals(all_messages.get(3).getId(), 7);

        Assertions.assertEquals(all_messages.get(0).getChatId(), 1);
        Assertions.assertEquals(all_messages.get(1).getChatId(), 1);
        Assertions.assertEquals(all_messages.get(2).getChatId(), 1);
        Assertions.assertEquals(all_messages.get(3).getChatId(), 1);

        Assertions.assertEquals(all_messages.get(0).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(1).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(2).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(3).getSender().getId(), 0);

        Assertions.assertEquals(all_messages.get(0).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(1).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(2).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(3).getSender().getLogin(), "adamenko");

        Assertions.assertEquals(all_messages.get(0).getText(), "1");
        Assertions.assertEquals(all_messages.get(1).getText(), "2");
        Assertions.assertEquals(all_messages.get(2).getText(), "3");
        Assertions.assertEquals(all_messages.get(3).getText(), "4");

        // -----------------------------------------------------------

        all_messages = messageService.getAllMessages(2);

        Assertions.assertEquals(all_messages.get(0).getId(), 8);
        Assertions.assertEquals(all_messages.get(1).getId(), 9);
        Assertions.assertEquals(all_messages.get(2).getId(), 10);
        Assertions.assertEquals(all_messages.get(3).getId(), 11);

        Assertions.assertEquals(all_messages.get(0).getChatId(), 2);
        Assertions.assertEquals(all_messages.get(1).getChatId(), 2);
        Assertions.assertEquals(all_messages.get(2).getChatId(), 2);
        Assertions.assertEquals(all_messages.get(3).getChatId(), 2);

        Assertions.assertEquals(all_messages.get(0).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(1).getSender().getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getSender().getId(), 2);
        Assertions.assertEquals(all_messages.get(3).getSender().getId(), 1);

        Assertions.assertEquals(all_messages.get(0).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(1).getSender().getLogin(), "zhdun");
        Assertions.assertEquals(all_messages.get(2).getSender().getLogin(), "adamada");
        Assertions.assertEquals(all_messages.get(3).getSender().getLogin(), "zhdun");

        Assertions.assertEquals(all_messages.get(0).getText(), "Wa");
        Assertions.assertEquals(all_messages.get(1).getText(), "Ok");
        Assertions.assertEquals(all_messages.get(2).getText(), "Re");
        Assertions.assertEquals(all_messages.get(3).getText(), "Pe");

        messageService.createMessage(new Message(12, new User(0, "adamenko", "qwerty", "adamenko", "male", 19, "YSDA 1st year student, BSU 2nd year student, swimmer"), 0, "Me too!", new Date(), new Time(1685135655)));

        all_messages = messageService.getAllMessages(0);
        Assertions.assertEquals(all_messages.size(), 5);

        Assertions.assertEquals(all_messages.get(0).getId(), 0);
        Assertions.assertEquals(all_messages.get(1).getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getId(), 2);
        Assertions.assertEquals(all_messages.get(3).getId(), 3);
        Assertions.assertEquals(all_messages.get(4).getId(), 12);

        Assertions.assertEquals(all_messages.get(0).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(1).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(2).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(3).getChatId(), 0);
        Assertions.assertEquals(all_messages.get(4).getChatId(), 0);

        Assertions.assertEquals(all_messages.get(0).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(1).getSender().getId(), 1);
        Assertions.assertEquals(all_messages.get(2).getSender().getId(), 0);
        Assertions.assertEquals(all_messages.get(3).getSender().getId(), 1);
        Assertions.assertEquals(all_messages.get(4).getSender().getId(), 0);

        Assertions.assertEquals(all_messages.get(0).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(1).getSender().getLogin(), "zhdun");
        Assertions.assertEquals(all_messages.get(2).getSender().getLogin(), "adamenko");
        Assertions.assertEquals(all_messages.get(3).getSender().getLogin(), "zhdun");
        Assertions.assertEquals(all_messages.get(4).getSender().getLogin(), "adamenko");

        Assertions.assertEquals(all_messages.get(0).getText(), "Hi! How are you?");
        Assertions.assertEquals(all_messages.get(1).getText(), "I am OK. And you?");
        Assertions.assertEquals(all_messages.get(2).getText(), "I am fine. What are doing tonight?");
        Assertions.assertEquals(all_messages.get(3).getText(), "I am coding a project:)");
        Assertions.assertEquals(all_messages.get(4).getText(), "Me too!");

        runCommand("src/test/clear_database.sh");
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