package project.dao.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.dao.Chat.ChatMapper;
import project.models.Message;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MessageDAO implements MessageService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void postConstruct() {
//        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS messages(id SERIAL PRIMARY KEY, sender_id int, receiver_id int, text varchar, dispatch_time timestamptz);");
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS chats(id SERIAL PRIMARY KEY, name varchar, is_group_chat boolean);");
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS chats_members(chat_id int, member_id int);");
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS messages(id SERIAL PRIMARY KEY, sender_id int, chat_id int, text varchar, dispatch_time timestamptz);");
    }

//    @Override
//    public List<Message> getAllMessages() {
//        return jdbcTemplate.query("SELECT * FROM messages;", new MessageMapper()).stream().toList();
//    }

    @Override
    public List<Message> getAllMessages(int chat_id) {
        return jdbcTemplate.query(
                """
                        SELECT messages.id AS id, chat_id, text, dispatch_time,
                         users.id AS sender_id, login AS sender_login, password AS sender_password
                        FROM messages
                            INNER JOIN users ON sender_id = users.id
                        WHERE chat_id = ?
                        ORDER BY dispatch_time ASC;""",
                new MessageMapper(),
                chat_id).stream().toList();
    }

    @Override
    public void createMessage(Message message) {
        jdbcTemplate.update("INSERT INTO messages(sender_id, chat_id, text, dispatch_time) VALUES (?, ?, ?, now());",
                message.getSender().getId(), message.getChatId(), message.getText());
    }

//    @Override
//    public void deleteMessage(int id) {
//        throw new RuntimeException("method not implemented");
//    }
//
//    @Override
//    public void updateMessage(Message message) {
//        jdbcTemplate.update("UPDATE messages SET text=? WHERE id=?",
//                message.getText(),
//                message.getId());
//    }

}
