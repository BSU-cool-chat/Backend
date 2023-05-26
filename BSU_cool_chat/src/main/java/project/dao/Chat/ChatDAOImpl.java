package project.dao.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.dao.IdMapper;
import project.models.Chat;
import project.models.User;

import java.util.List;
import java.util.Optional;

@Component
public class ChatDAOImpl implements ChatDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Chat> getAllUsersChats(int user_id) {
        return jdbcTemplate.query("""
                SELECT chat_id, name, is_group_chat
                FROM chats_members
                        JOIN chats ON chat_id = id
                WHERE member_id = ?;""", new ChatMapper(), user_id).stream().toList();
    }

    @Override
    public Chat getChat(int chat_id) {
        Optional<Chat> chat = jdbcTemplate.query("""
                SELECT id AS chat_id, name, is_group_chat
                FROM chats
                WHERE id = ?;""", new ChatMapper(), chat_id).stream().findAny();
        if (chat.isEmpty()) {
            throw new RuntimeException("Chat not found");
        }
        return chat.get();
    }

    @Override
    public Chat getOrCreateStandardChat(User user1, User user2) {
        if (user1.getId() == user2.getId()) {
            throw new RuntimeException("Cannot create chat between the same users");
        }
        var searching_result = jdbcTemplate.query("""
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
                WHERE is_group_chat = false;""", new ChatMapper(), user1.getId(), user2.getId()).stream().findAny();
        return searching_result.orElseGet(() -> CreateStandardChat(user1, user2));
    }

    private Chat CreateStandardChat(User user1, User user2) {
        String chat_name = "Chat between " + user1.getLogin() + " and " + user2.getLogin();
        int chat_id = jdbcTemplate.query("""
                INSERT INTO chats(name, is_group_chat)
                VALUES (?, ?)
                RETURNING id""", new IdMapper(), chat_name, false).stream().findAny().get();
        jdbcTemplate.update("""
                INSERT INTO chats_members(chat_id, member_id)
                VALUES (?, ?), (?, ?)""", chat_id, user1.getId(), chat_id, user2.getId());
        Chat new_chat = new Chat(chat_id, chat_name, false);
        new_chat.setMembers(List.of(user1, user2));
        return new_chat;
    }

    @Override
    public List<Integer> getAllChatMembers(int chat_id) {
        return jdbcTemplate.query("""
                        SELECT member_id AS id
                        FROM chats_members
                        WHERE chat_id = ?;""",
                new IdMapper(), chat_id).stream().toList();
    }
}