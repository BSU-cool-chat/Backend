package project.dao.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.dao.Message.MessageDAO;
import project.dao.Message.MessageService;
import project.dao.User.UserService;
import project.models.Chat;
import project.models.ChatInfo;
import project.models.Message;
import project.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatDAO implements ChatService {
    private final JdbcTemplate jdbcTemplate;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public ChatDAO(JdbcTemplate jdbcTemplate, MessageDAO messageService, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.messageService = messageService;
        this.userService = userService;
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
            chat.setMessages(messageService.getAllMessages(chat.getId()));
            chat.setMembers(userService.getAllChatMembers(chat.getId()));
        }
        return chats;
    }

    @Override
    public List<ChatInfo> getAllUsersChatsInfo(int user_id) {
//        TODO maybe better
        return getAllUsersChats(user_id).stream()
                .filter(chat -> !chat.getMessages().isEmpty())
                .map(chat -> chat.getChatInfo(user_id))
                .toList();
    }

    @Override
    public Chat getUsersChat(int user_id, int chat_id) {
//        TODO maybe better
        return getAllUsersChats(user_id).stream().filter(chat -> chat.getId() == chat_id).findAny().get();
    }

    @Override
    public Chat getOrCreateStandardChat(int user1_id, int user2_id) {
        var searching_result = jdbcTemplate.query(
                """
                        SELECT id AS chat_id, name, is_group_chat
                        FROM    (SELECT *
                                FROM chats_members
                                WHERE member_id = ?) q1
                                INNER JOIN
                                (SELECT *
                                FROM chats_members
                                WHERE member_id = ?) q2
                                ON q1.chat_id = q2.chat_id
                                INNER JOIN chats ON q1.chat_id = chats.id
                        WHERE is_group_chat = false;""",
                new ChatMapper(),
                user1_id, user2_id).stream().findAny();
        return searching_result.orElseGet(() -> CreateStandardChat(user1_id, user2_id));
    }

    @Override
    public void createMessage(Message message) {
        messageService.createMessage(message);
    }

    static class IntMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return resultSet.getInt("lst");
        }
    }

    private Chat CreateStandardChat(int user1_id, int user2_id) {
        User user1 = userService.getUser(user1_id);
        User user2 = userService.getUser(user2_id);
        int id = 1 + jdbcTemplate.query("""
                        SELECT MAX(id) AS lst FROM chats;
                        """,
                new IntMapper()).stream().findAny().get();
        String chat_name = "Chat between " + user1.getLogin() + " and " + user2.getLogin();
        Chat new_chat = new Chat(id, chat_name, false);
        new_chat.setMembers(List.of(user1, user2));
        jdbcTemplate.update("""
                INSERT INTO chats(name, is_group_chat) VALUES (?, ?)""", chat_name, false);
        jdbcTemplate.update("""
                INSERT INTO chats_members(chat_id, member_id) VALUES (?, ?), (?, ?)""", id, user1_id, id, user2_id);
        return new_chat;
    }
}
