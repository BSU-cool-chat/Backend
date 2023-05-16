package project.dao.Chat;

import org.springframework.jdbc.core.RowMapper;
import project.models.Chat;
import project.models.ChatInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMapper implements RowMapper<Chat> {
    @Override
    public Chat mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Chat(
        resultSet.getInt("chat_id"),
        resultSet.getString("name"),
        resultSet.getBoolean("is_group_chat"));
    }
}
