package project.dao.Chat;

import org.springframework.jdbc.core.RowMapper;
import project.models.ChatInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatInfoMapper implements RowMapper<ChatInfo> {
    @Override
    public ChatInfo mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new ChatInfo(resultSet.getInt("sender_id"),
                resultSet.getString("sender_login"),
                resultSet.getInt("receiver_id"),
                resultSet.getString("receiver_login"),
                resultSet.getString("text"),
                resultSet.getDate("dispatch_time"),
                resultSet.getTime("dispatch_time"),
                resultSet.getInt("interlocutor"));
    }
}
