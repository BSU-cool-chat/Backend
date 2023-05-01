package project.dao.Message;

import org.springframework.jdbc.core.RowMapper;
import project.models.Message;
import project.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Message message = new Message();

        message.setId(resultSet.getInt("id"));
        message.setSenderId(resultSet.getInt("sender_id"));
        message.setReceiverId(resultSet.getInt("receiver_id"));
        message.setText(resultSet.getString("text"));
        message.setDispatchTime(resultSet.getDate("dispatch_time"));

        return message;
    }
}
