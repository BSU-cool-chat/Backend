package project.dao.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.config.databases.DatabaseInitializer;
import project.models.Message;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MessageDAO implements MessageService {
    private final DatabaseInitializer databaseInitializer;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDAO(DatabaseInitializer databaseInitializer, JdbcTemplate jdbcTemplate) {
        this.databaseInitializer = databaseInitializer;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void postConstruct() {
        databaseInitializer.initDatabases();
    }

    @Override
    public List<Message> getAllMessages(int chat_id) {
        return jdbcTemplate.query(
                """
                        SELECT messages.id AS id, chat_id, text, dispatch_time,
                         users.id AS sender_id, login AS sender_login, password AS sender_password,
                         name AS sender_name, sex AS sender_sex, age AS sender_age,
                         additional_info AS sender_additional_info
                        FROM messages
                            INNER JOIN users ON sender_id = users.id
                            INNER JOIN users_info ON users.id = users_info.user_id
                        WHERE chat_id = ?
                        ORDER BY dispatch_time ASC;""",
                new MessageMapper(),
                chat_id).stream().toList();
    }

    @Override
    public void createMessage(Message message) {
        jdbcTemplate.update("""
                INSERT INTO messages(sender_id, chat_id, text, dispatch_time)
                VALUES (?, ?, ?, now());
                """, message.getSender().getId(), message.getChatId(), message.getText());
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
