package project.dao.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.config.databases.DatabaseInitializer;
import project.models.Message;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MessageDAOImpl implements MessageDAO {
    private final DatabaseInitializer databaseInitializer;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDAOImpl(DatabaseInitializer databaseInitializer, JdbcTemplate jdbcTemplate) {
        this.databaseInitializer = databaseInitializer;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Message> getAllMessages(int chat_id) {
        return jdbcTemplate.query(
                """
                        SELECT messages.id AS id, chat_id, text, dispatch_time, sender_id
                        FROM messages
                        WHERE chat_id = ?
                        ORDER BY dispatch_time;""",
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
}
