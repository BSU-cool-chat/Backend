package project.dao.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import project.models.Message;

@Component
public class MessageDAO implements MessageService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS messages(id SERIAL PRIMARY KEY, sender_id int, receiver_id int, text varchar, dispatch_time timestamptz)");
    }

    @Override
    public Iterable<Message> getAllMessages() {
        return jdbcTemplate.query("SELECT * FROM users", new MessageMapper());
    }

    @Override
    public void createMessage(Message message) {
        jdbcTemplate.update("INSERT INTO messages(sender_id, receiver_id, text, dispatch_time) VALUES (?, ?, ?, ?);",
                message.getSenderId(), message.getReceiverId(), message.getText(), message.getDispatchTime());
    }

    @Override
    public void deleteMessage(int id) {
        throw new RuntimeException("method not implemented");
    }

    @Override
    public void updateMessage(Message message) {
        jdbcTemplate.update("UPDATE messages SET sender_id=?, receiver_id=?, text=?, dispatch_time=? WHERE id=?",
                message.getSenderId(),
                message.getReceiverId(),
                message.getText(),
                message.getDispatchTime(),
                message.getId());
    }
}
