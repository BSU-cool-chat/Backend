package project.dao.Message;

import org.springframework.jdbc.core.RowMapper;
import project.models.Message;
import project.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        User sender = new User();
        sender.setId(resultSet.getInt("sender_id"));

        return new Message(resultSet.getInt("id"),
                sender,
                resultSet.getInt("chat_id"),
                resultSet.getString("text"),
                resultSet.getDate("dispatch_time"),
                resultSet.getTime("dispatch_time"));
    }
}
