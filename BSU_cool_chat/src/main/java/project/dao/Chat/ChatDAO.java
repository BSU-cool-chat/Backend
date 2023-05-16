package project.dao.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.dao.Message.MessageDAO;
import project.dao.User.UserDAO;
import project.models.Chat;
import project.models.ChatInfo;

import java.util.List;

@Component
public class ChatDAO implements ChatService {
    private final JdbcTemplate jdbcTemplate;
    private final MessageDAO messageDAO;
    private final UserDAO userDAO;

    @Autowired
    public ChatDAO(JdbcTemplate jdbcTemplate, MessageDAO messageDAO, UserDAO userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Chat> getAllUsersChats(int user_id) {
        var chats = jdbcTemplate.query(
                """
                        SELECT chat_id, name, is_group_chat
                        FROM chats_members
                                INNER JOIN chats ON chat_id = id
                        WHERE member_id = ?;""",
                new ChatMapper(),
                user_id).stream().toList();
        for (var chat : chats) {
            chat.setMessages(messageDAO.getAllMessages(chat.getId()));
            chat.setMembers(userDAO.getAllChatMembers(chat.getId()));
        }
        return chats;
    }

    @Override
    public List<ChatInfo> getAllUsersChatsInfo(int user_id) {
//        TODO maybe better
        return getAllUsersChats(user_id).stream().map(Chat::getChatInfo).toList();
    }
}
