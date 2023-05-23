package project.dao.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.dao.User.UserDAO;
import project.dao.User.UserMapper;
import project.dao.User.UserService;
import project.models.Message;
import project.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        User sender = new User(
                resultSet.getInt("sender_id"),
                resultSet.getString("sender_login"),
                resultSet.getString("sender_password"),
                resultSet.getString("sender_name"),
                resultSet.getString("sender_sex"),
                resultSet.getInt("sender_age"),
                resultSet.getString("sender_additional_info")
        );

        return new Message(resultSet.getInt("id"),
                sender,
                resultSet.getInt("chat_id"),
                resultSet.getString("text"),
                resultSet.getDate("dispatch_time"),
                resultSet.getTime("dispatch_time"));
    }
}
